import axios from 'axios';
import type { AxiosError, AxiosInstance, AxiosResponse } from 'axios';
import useAuthStore from '../stores/authStore';

// Using Vite proxy in development, so we can use relative URLs
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || ''; // Empty string for relative URLs

const apiClient: AxiosInstance = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
  withCredentials: true,
});

// Request interceptor to add auth token
apiClient.interceptors.request.use(
  (config) => {
    const token = useAuthStore.getState().token;
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Response interceptor for handling errors
apiClient.interceptors.response.use(
  (response: AxiosResponse) => response,
  async (error: AxiosError) => {
    const originalRequest = error.config as any;
    
    // Handle 401 Unauthorized
    if (error.response?.status === 401) {
      // If this is a retry or not a refresh token request
      if (!originalRequest?._retry && !originalRequest?.url?.includes('refresh-token')) {
        originalRequest._retry = true;
        
        try {
          // Try to refresh token
          const { data } = await axios.post(`${API_BASE_URL}/api/auth/refresh-token`, {}, { 
            withCredentials: true,
            headers: {
              'Content-Type': 'application/json'
            }
          });
          
          const { token, user } = data;
          
          if (token) {
            // Update the token in the store
            useAuthStore.getState().login(token, user);
            
            // Update the Authorization header
            originalRequest.headers = originalRequest.headers || {};
            originalRequest.headers.Authorization = `Bearer ${token}`;
            
            // Retry the original request
            return apiClient(originalRequest);
          }
        } catch (refreshError) {
          console.error('Token refresh failed:', refreshError);
          // If refresh token fails, log the user out
          useAuthStore.getState().logout();
          window.location.href = '/login';
          return Promise.reject(refreshError);
        }
      }
      
      // If we already tried to refresh or it's a refresh request, log out
      useAuthStore.getState().logout();
      window.location.href = '/login';
      return Promise.reject(error);
    }
    
    return Promise.reject(error);
  }
);

export default apiClient;

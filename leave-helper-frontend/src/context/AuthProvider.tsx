import { ReactNode, useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { toast } from 'react-hot-toast';
import useAuthStore from '../stores/authStore';
import authApi from '../api/auth';

interface AuthProviderProps {
  children: ReactNode;
}

const AuthProvider = ({ children }: AuthProviderProps) => {
  const { isAuthenticated, setUser, setToken, logout } = useAuthStore();
  const navigate = useNavigate();
  const location = useLocation();

  // Check authentication status on initial load
  useEffect(() => {
    const checkAuth = async () => {
      try {
        const user = await authApi.getCurrentUser();
        setUser(user);
        
        // Redirect to dashboard if user is on auth pages
        if (location.pathname === '/login' || location.pathname === '/register') {
          navigate('/dashboard', { replace: true });
        }
      } catch (error) {
        // If not authenticated, redirect to login if on protected route
        if (!location.pathname.startsWith('/auth')) {
          navigate('/login', { 
            state: { 
              from: location,
              message: 'Please log in to continue' 
            },
            replace: true 
          });
        }
      }
    };

    if (isAuthenticated) {
      checkAuth();
    }
  }, [isAuthenticated, setUser, navigate, location]);

  // Handle token refresh
  useEffect(() => {
    const refreshToken = async () => {
      try {
        const { token } = await authApi.refreshToken();
        setToken(token);
      } catch (error) {
        logout();
        navigate('/login', { replace: true });
      }
    };

    // Set up token refresh interval (e.g., every 10 minutes)
    const interval = setInterval(() => {
      if (isAuthenticated) {
        refreshToken();
      }
    }, 10 * 60 * 1000);

    // Clean up interval on unmount
    return () => clearInterval(interval);
  }, [isAuthenticated, setToken, logout, navigate]);

  return <>{children}</>;
};

export default AuthProvider;

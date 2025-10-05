import axios from 'axios';
import type { CreateTicketRequest, Ticket } from '../types/ticket';
import useAuthStore from '../stores/authStore';

const API_BASE_URL = '/tickets'; // The /api prefix is added by the proxy in vite.config.ts

// Create axios instance with default config
const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Add a request interceptor to include the auth token
api.interceptors.request.use(
  (config) => {
    const { token } = useAuthStore.getState();
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export const ticketService = {
  // Create a new ticket
  createTicket: async (data: CreateTicketRequest): Promise<Ticket> => {
    const response = await api.post('', data);
    return response.data;
  },

  // Get current user's tickets
  getMyTickets: async (): Promise<Ticket[]> => {
    const response = await api.get('/my-tickets');
    // Handle both direct array response and nested data property
    return Array.isArray(response.data) ? response.data : response.data?.data || [];
  },

  // Get pending tickets (admin only)
  getPendingTickets: async (): Promise<Ticket[]> => {
    const response = await api.get('/pending');
    // Handle both direct array response and nested data property
    return Array.isArray(response.data) ? response.data : response.data?.data || [];
  },

  // Approve a ticket (admin only)
  approveTicket: async (id: number): Promise<Ticket> => {
    const response = await api.put(`/${id}/approve`);
    return response.data;
  },

  // Reject a ticket (admin only)
  rejectTicket: async (id: number, comments?: string): Promise<Ticket> => {
    const response = await api.put(`/${id}/reject`, null, {
      params: { comments },
    });
    return response.data;
  },
};

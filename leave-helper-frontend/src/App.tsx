import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { ReactQueryDevtools } from '@tanstack/react-query-devtools';
import { Toaster } from 'react-hot-toast';
import { Routes, Route, Navigate } from 'react-router-dom';
import AuthProvider from './context/AuthProvider';
import ProtectedRoute from './components/auth/ProtectedRoute';
import MainLayout from './components/layout/MainLayout';
import Dashboard from './pages/dashboard';
import Login from './pages/auth/Login';
import Register from './pages/auth/Register';
import Profile from './pages/profile/Profile';
import TicketsPage from './pages/tickets';
import TicketList from './pages/tickets/TicketList';
import CreateTicketForm from './pages/tickets/CreateTicketForm';
import './App.css';

// Create a client
const queryClient = new QueryClient();

// Main App Component with Routing
function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <AuthProvider>
        <Toaster position="top-right" />
        <Routes>
          {/* Public Routes */}
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />

          {/* Protected Routes */}
          <Route
            path="/"
            element={
              <ProtectedRoute>
                <MainLayout />
              </ProtectedRoute>
            }
          >
            <Route index element={<Dashboard />} />
            <Route path="profile" element={<Profile />} />
            <Route path="tickets" element={<TicketsPage />}>
              <Route index element={<TicketList />} />
              <Route path="create" element={<CreateTicketForm />} />
              <Route path="pending" element={<TicketList showAllPending />} />
            </Route>
            <Route path="*" element={<Navigate to="/" replace />} />
          </Route>
          {/* Error Pages */}
          <Route path="/unauthorized" element={<div>Unauthorized</div>} />
          <Route path="*" element={<div>404 - Page Not Found</div>} />
        </Routes>
        
        {/* React Query DevTools - Only in development */}
        {import.meta.env.DEV && <ReactQueryDevtools initialIsOpen={false} />}
      </AuthProvider>
    </QueryClientProvider>
  );
}

export default App

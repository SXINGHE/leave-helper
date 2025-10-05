import { format } from 'date-fns';
import type { LeaveType, LeaveStatus, UserRole } from '../types';

export const formatDate = (date: Date | string, formatStr = 'MMM d, yyyy'): string => {
  const dateObj = typeof date === 'string' ? new Date(date) : date;
  return format(dateObj, formatStr);
};

export const formatDateTime = (date: Date | string): string => {
  return formatDate(date, 'MMM d, yyyy h:mm a');
};

export const calculateWorkingDays = (startDate: Date | string, endDate: Date | string): number => {
  const start = typeof startDate === 'string' ? new Date(startDate) : startDate;
  const end = typeof endDate === 'string' ? new Date(endDate) : endDate;
  
  let count = 0;
  let current = new Date(start);
  
  while (current <= end) {
    const day = current.getDay();
    // Count only weekdays (0 = Sunday, 6 = Saturday)
    if (day !== 0 && day !== 6) {
      count++;
    }
    current.setDate(current.getDate() + 1);
  }
  
  return count;
};

export const getLeaveTypeLabel = (type: LeaveType): string => {
  const types: Record<LeaveType, string> = {
    VACATION: 'Vacation',
    SICK: 'Sick Leave',
    PERSONAL: 'Personal Day',
    UNPAID: 'Unpaid Leave',
    OTHER: 'Other'
  };
  return types[type] || type;
};

export const getStatusBadgeVariant = (status: LeaveStatus): string => {
  const variants: Record<LeaveStatus, string> = {
    PENDING: 'bg-yellow-100 text-yellow-800',
    APPROVED: 'bg-green-100 text-green-800',
    REJECTED: 'bg-red-100 text-red-800',
    CANCELLED: 'bg-gray-100 text-gray-800',
    DRAFT: 'bg-gray-100 text-gray-800',
    SUBMITTED: 'bg-blue-100 text-blue-800',
    IN_REVIEW: 'bg-purple-100 text-purple-800',
    ON_HOLD: 'bg-orange-100 text-orange-800',
    EXPIRED: 'bg-gray-300 text-gray-800',
  };
  return variants[status] || 'bg-gray-100 text-gray-800';
};

export const getRoleLabel = (role: UserRole): string => {
  const roles: Record<UserRole, string> = {
    ADMIN: 'Administrator',
    MANAGER: 'Manager',
    USER: 'User'
  };
  return roles[role] || role;
};

export const formatFileSize = (bytes: number): string => {
  if (bytes === 0) return '0 Bytes';
  
  const k = 1024;
  const sizes = ['Bytes', 'KB', 'MB', 'GB'];
  const i = Math.floor(Math.log(bytes) / Math.log(k));
  
  return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i];
};

export const debounce = <F extends (...args: any[]) => any>(
  func: F,
  wait: number
): ((...args: Parameters<F>) => void) => {
  let timeout: ReturnType<typeof setTimeout> | null = null;
  
  return function(...args: Parameters<F>) {
    const later = () => {
      timeout = null;
      func(...args);
    };
    
    if (timeout !== null) {
      clearTimeout(timeout);
    }
    
    timeout = setTimeout(later, wait);
  };
};

export const isAdmin = (user: { role: UserRole } | null | undefined): boolean => {
  return user?.role === 'ADMIN';
};

export const isManager = (user: { role: UserRole } | null | undefined): boolean => {
  return user?.role === 'MANAGER' || isAdmin(user);
};

export const hasPermission = (
  user: { role: UserRole } | null | undefined,
  requiredRole: UserRole
): boolean => {
  if (!user) return false;
  
  const roleHierarchy: Record<UserRole, number> = {
    ADMIN: 3,
    MANAGER: 2,
    USER: 1
  };
  
  return roleHierarchy[user.role] >= roleHierarchy[requiredRole];
};

export const generateInitials = (name: string): string => {
  return name
    .split(' ')
    .map(part => part[0])
    .join('')
    .toUpperCase()
    .substring(0, 2);
};

export const validateEmail = (email: string): boolean => {
  const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return re.test(email);
};

export const formatCurrency = (amount: number, currency = 'USD'): string => {
  return new Intl.NumberFormat('en-US', {
    style: 'currency',
    currency,
    minimumFractionDigits: 2
  }).format(amount);
};

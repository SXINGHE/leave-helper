// User related types
export interface User {
  id: string;
  username: string;
  email: string;
  firstName: string;
  lastName: string;
  role: UserRole;
  department?: string;
  position?: string;
  hireDate?: string;
  phone?: string;
  avatar?: string;
}

export type UserRole = 'ADMIN' | 'MANAGER' | 'USER';

// Leave related types
export type LeaveType = 'VACATION' | 'SICK' | 'PERSONAL' | 'UNPAID' | 'OTHER';

export type LeaveStatus = 'PENDING' | 'APPROVED' | 'REJECTED' | 'CANCELLED' | 'DRAFT' | 'SUBMITTED' | 'IN_REVIEW' | 'ON_HOLD' | 'EXPIRED';

export interface LeaveRequest {
  id: string;
  userId: string;
  user?: Pick<User, 'id' | 'firstName' | 'lastName' | 'email'>;
  type: LeaveType;
  status: LeaveStatus;
  startDate: string;
  endDate: string;
  days: number;
  reason: string;
  notes?: string;
  createdAt: string;
  updatedAt: string;
  approvedBy?: string;
  approvedAt?: string;
}

// Team related types
export interface TeamMember {
  id: string;
  user: User;
  role: TeamRole;
  joinDate: string;
  leaveBalance: number;
  pendingLeaves: number;
  approvedLeaves: number;
}

export type TeamRole = 'MEMBER' | 'LEAD' | 'MANAGER';

// API response types
export interface ApiResponse<T> {
  data: T;
  message: string;
  success: boolean;
}

export interface PaginatedResponse<T> {
  items: T[];
  total: number;
  page: number;
  limit: number;
  totalPages: number;
  hasNext: boolean;
  hasPrev: boolean;
}

// Form data types
export interface LoginFormData {
  username: string;
  password: string;
  rememberMe?: boolean;
}

export interface RegisterFormData {
  username: string;
  email: string;
  password: string;
  confirmPassword: string;
  firstName: string;
  lastName: string;
}

export interface LeaveRequestFormData {
  type: LeaveType;
  startDate: string;
  endDate: string;
  reason: string;
  notes?: string;
}

// UI related types
export interface NavItem {
  name: string;
  href: string;
  icon: React.ComponentType<React.SVGProps<SVGSVGElement>>;
  current: boolean;
  children?: NavItem[];
  roles?: UserRole[];
}

export interface DropdownItem {
  name: string;
  href?: string;
  icon?: React.ComponentType<React.SVGProps<SVGSVGElement>>;
  onClick?: () => void;
  divider?: boolean;
  danger?: boolean;
}

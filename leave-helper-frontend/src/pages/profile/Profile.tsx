import { useState, useEffect } from 'react';
import { useForm, type SubmitHandler } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from 'yup';
import { useQuery, useMutation } from '@tanstack/react-query';
import { toast } from 'react-hot-toast';
import type { User, UserRole } from '../../types';
import { getRoleLabel } from '../../utils';
import authApi from '../../api/auth';
import useAuthStore from '../../stores/authStore';

interface UserResponse extends Omit<User, 'role'> {
  role: string | UserRole;
}

// Form validation schema
const profileSchema = yup.object({
  firstName: yup.string().required('First name is required'),
  lastName: yup.string().required('Last name is required'),
  email: yup.string().email('Invalid email').required('Email is required'),
  phone: yup.string()
    .matches(/^[0-9\-\+\s]*$/, 'Invalid phone number')
    .optional(),
  department: yup.string().optional(),
  position: yup.string().optional()
}).required();

type ProfileFormData = yup.InferType<typeof profileSchema>;

export default function Profile() {
  const { user: currentUser } = useAuthStore();
  const [isEditing, setIsEditing] = useState(false);
  const { setUser } = useAuthStore();

  // Fetch user data
  const { data: userData, isLoading } = useQuery<UserResponse>({
    queryKey: ['user', currentUser?.id],
    queryFn: () => authApi.getCurrentUser(),
    enabled: !!currentUser?.id,
    select: (data) => ({
      ...data,
      role: data.role as UserRole
    })
  });
  
  const user = userData as User | undefined;

  // Update profile mutation
  const updateProfileMutation = useMutation({
    mutationFn: async (data: ProfileFormData) => {
      // In a real app, this would be an API call to update the user
      const updatedUser: User = {
        ...user!,
        ...data,
        // Ensure required fields are preserved
        id: user?.id || '',
        username: user?.username || '',
        role: user?.role || 'USER',
        // Convert empty strings to undefined for optional fields
        phone: data.phone || undefined,
        department: data.department || undefined,
        position: data.position || undefined
      };
      return new Promise<User>((resolve) => {
        setTimeout(() => resolve(updatedUser), 500);
      });
    },
    onSuccess: (data) => {
      setUser(data);
      setIsEditing(false);
      toast.success('Profile updated successfully');
    },
    onError: (error: any) => {
      toast.error(error.response?.data?.message || 'Failed to update profile');
    },
  });
  
  const isUpdating = updateProfileMutation.isPending || false;

  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm<ProfileFormData>({
    // @ts-ignore - Type mismatch between yup and react-hook-form
    resolver: yupResolver(profileSchema) as any,
    defaultValues: {
      firstName: user?.firstName || '',
      lastName: user?.lastName || '',
      email: user?.email || '',
      phone: user?.phone || '',
      department: user?.department || '',
      position: user?.position || ''
    }
  });

  // Reset form when user data changes
  useEffect(() => {
    if (user) {
      reset({
        firstName: user.firstName,
        lastName: user.lastName,
        email: user.email,
        phone: user.phone || '',
        department: user.department || '',
        position: user.position || ''
      });
    }
  }, [user, reset]);

  // Form submission handler
  const onSubmit: SubmitHandler<ProfileFormData> = (data) => {
    updateProfileMutation.mutate(data);
  };

  if (isLoading || !user) {
    return (
      <div className="flex justify-center items-center h-64">
        <div className="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-indigo-500"></div>
      </div>
    );
  }

  return (
    <div className="space-y-6">
      <div className="md:flex md:items-center md:justify-between">
        <div className="min-w-0 flex-1">
          <h2 className="text-2xl font-bold leading-7 text-gray-900 sm:truncate sm:text-3xl sm:tracking-tight">
            Profile
          </h2>
        </div>
        <div className="mt-4 flex md:ml-4 md:mt-0">
          {!isEditing && (
            <button
              type="button"
              onClick={() => setIsEditing(true)}
              className="ml-3 inline-flex items-center rounded-md bg-indigo-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
            >
              Edit Profile
            </button>
          )}
        </div>
      </div>

      <div className="bg-white shadow sm:rounded-lg">
        <div className="px-4 py-5 sm:px-6">
          <h3 className="text-lg font-medium leading-6 text-gray-900">Personal Information</h3>
          <p className="mt-1 max-w-2xl text-sm text-gray-500">
            {isEditing
              ? 'Update your personal information.'
              : 'Your personal information and account details.'}
          </p>
        </div>
        
        <form onSubmit={handleSubmit(onSubmit)}>
          <div className="border-t border-gray-200 px-4 py-5 sm:px-6">
            <dl className="grid grid-cols-1 gap-x-4 gap-y-8 sm:grid-cols-2">
              <div className="sm:col-span-1">
                <dt className="text-sm font-medium text-gray-500">Username</dt>
                <dd className="mt-1 text-sm text-gray-900">{user.username}</dd>
              </div>
              
              <div className="sm:col-span-1">
                <dt className="text-sm font-medium text-gray-500">Role</dt>
                <dd className="mt-1 text-sm text-gray-900">{getRoleLabel(user.role)}</dd>
              </div>
              
              <div className="sm:col-span-1">
                <dt className="text-sm font-medium text-gray-500">
                  <label htmlFor="firstName" className="block">First name</label>
                </dt>
                {isEditing ? (
                  <>
                    <input
                      type="text"
                      id="firstName"
                      {...register('firstName')}
                      className={`mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm ${
                        errors.firstName ? 'border-red-300' : 'border-gray-300'
                      }`}
                    />
                    {errors.firstName && (
                      <p className="mt-1 text-sm text-red-600">{errors.firstName.message}</p>
                    )}
                  </>
                ) : (
                  <dd className="mt-1 text-sm text-gray-900">{user.firstName}</dd>
                )}
              </div>
              
              <div className="sm:col-span-1">
                <dt className="text-sm font-medium text-gray-500">
                  <label htmlFor="lastName" className="block">Last name</label>
                </dt>
                {isEditing ? (
                  <>
                    <input
                      type="text"
                      id="lastName"
                      {...register('lastName')}
                      className={`mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm ${
                        errors.lastName ? 'border-red-300' : 'border-gray-300'
                      }`}
                    />
                    {errors.lastName && (
                      <p className="mt-1 text-sm text-red-600">{errors.lastName.message}</p>
                    )}
                  </>
                ) : (
                  <dd className="mt-1 text-sm text-gray-900">{user.lastName}</dd>
                )}
              </div>
              
              <div className="sm:col-span-1">
                <dt className="text-sm font-medium text-gray-500">
                  <label htmlFor="email" className="block">Email</label>
                </dt>
                {isEditing ? (
                  <>
                    <input
                      type="email"
                      id="email"
                      {...register('email')}
                      className={`mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm ${
                        errors.email ? 'border-red-300' : 'border-gray-300'
                      }`}
                    />
                    {errors.email && (
                      <p className="mt-1 text-sm text-red-600">{errors.email.message}</p>
                    )}
                  </>
                ) : (
                  <dd className="mt-1 text-sm text-gray-900">{user.email}</dd>
                )}
              </div>
              
              <div className="sm:col-span-1">
                <dt className="text-sm font-medium text-gray-500">
                  <label htmlFor="phone" className="block">Phone</label>
                </dt>
                {isEditing ? (
                  <>
                    <input
                      type="tel"
                      id="phone"
                      {...register('phone')}
                      className={`mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm ${
                        errors.phone ? 'border-red-300' : 'border-gray-300'
                      }`}
                    />
                    {errors.phone && (
                      <p className="mt-1 text-sm text-red-600">{errors.phone.message}</p>
                    )}
                  </>
                ) : (
                  <dd className="mt-1 text-sm text-gray-900">{user.phone || 'Not provided'}</dd>
                )}
              </div>
              
              <div className="sm:col-span-1">
                <dt className="text-sm font-medium text-gray-500">
                  <label htmlFor="department" className="block">Department</label>
                </dt>
                {isEditing ? (
                  <input
                    type="text"
                    id="department"
                    {...register('department')}
                    className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                  />
                ) : (
                  <dd className="mt-1 text-sm text-gray-900">{user.department || 'Not specified'}</dd>
                )}
              </div>
              
              <div className="sm:col-span-1">
                <dt className="text-sm font-medium text-gray-500">
                  <label htmlFor="position" className="block">Position</label>
                </dt>
                {isEditing ? (
                  <input
                    type="text"
                    id="position"
                    {...register('position')}
                    className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                  />
                ) : (
                  <dd className="mt-1 text-sm text-gray-900">{user.position || 'Not specified'}</dd>
                )}
              </div>
            </dl>
          </div>
          
          {isEditing && (
            <div className="bg-gray-50 px-4 py-3 text-right sm:px-6">
              <button
                type="button"
                onClick={() => {
                  reset();
                  setIsEditing(false);
                }}
                className="mr-3 rounded-md border border-gray-300 bg-white py-2 px-4 text-sm font-medium text-gray-700 shadow-sm hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"
              >
                Cancel
              </button>
              <button
                type="submit"
                disabled={isUpdating}
                className="inline-flex justify-center rounded-md border border-transparent bg-indigo-600 py-2 px-4 text-sm font-medium text-white shadow-sm hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2 disabled:opacity-50"
              >
                {isUpdating ? 'Saving...' : 'Save Changes'}
              </button>
            </div>
          )}
        </form>
      </div>

      <div className="bg-white shadow sm:rounded-lg">
        <div className="px-4 py-5 sm:px-6">
          <h3 className="text-lg font-medium leading-6 text-gray-900">Account Security</h3>
          <p className="mt-1 max-w-2xl text-sm text-gray-500">
            Manage your password and security settings
          </p>
        </div>
        <div className="border-t border-gray-200 px-4 py-5 sm:px-6">
          <div className="flex items-center justify-between">
            <div>
              <h4 className="text-sm font-medium text-gray-900">Password</h4>
              <p className="text-sm text-gray-500">Last changed 3 months ago</p>
            </div>
            <button
              type="button"
              className="inline-flex items-center rounded-md border border-gray-300 bg-white px-4 py-2 text-sm font-medium text-gray-700 shadow-sm hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"
            >
              Change Password
            </button>
          </div>
        </div>
      </div>
      
      <div className="bg-white shadow sm:rounded-lg">
        <div className="px-4 py-5 sm:px-6">
          <h3 className="text-lg font-medium leading-6 text-gray-900">Leave Balance</h3>
          <p className="mt-1 max-w-2xl text-sm text-gray-500">
            Your available leave days
          </p>
        </div>
        <div className="border-t border-gray-200 px-4 py-5 sm:px-6">
          <div className="grid grid-cols-1 gap-4 sm:grid-cols-3">
            <div className="overflow-hidden rounded-lg bg-white px-4 py-5 shadow sm:p-6">
              <dt className="truncate text-sm font-medium text-gray-500">Annual Leave</dt>
              <dd className="mt-1 text-3xl font-semibold tracking-tight text-gray-900">12.5</dd>
              <p className="mt-1 text-sm text-gray-500">Out of 20 days</p>
            </div>
            <div className="overflow-hidden rounded-lg bg-white px-4 py-5 shadow sm:p-6">
              <dt className="truncate text-sm font-medium text-gray-500">Sick Leave</dt>
              <dd className="mt-1 text-3xl font-semibold tracking-tight text-gray-900">7</dd>
              <p className="mt-1 text-sm text-gray-500">Out of 10 days</p>
            </div>
            <div className="overflow-hidden rounded-lg bg-white px-4 py-5 shadow sm:p-6">
              <dt className="truncate text-sm font-medium text-gray-500">Personal Days</dt>
              <dd className="mt-1 text-3xl font-semibold tracking-tight text-gray-900">2</dd>
              <p className="mt-1 text-sm text-gray-500">Out of 5 days</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

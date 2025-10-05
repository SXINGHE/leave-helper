import { useState } from 'react';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from 'yup';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-hot-toast';
import { addDays, format, isAfter, isBefore, parseISO } from 'date-fns';
import { calculateWorkingDays } from '../../utils';

// Form validation schema
const leaveRequestSchema = yup.object().shape({
  type: yup.string().required('Leave type is required'),
  startDate: yup.date().required('Start date is required')
    .test(
      'is-future',
      'Start date must be today or in the future',
      (value) => {
        const today = new Date();
        today.setHours(0, 0, 0, 0);
        return !isBefore(new Date(value), today);
      }
    ),
  endDate: yup.date()
    .required('End date is required')
    .test(
      'is-after-start',
      'End date must be after start date',
      function(value) {
        const { startDate } = this.parent;
        return !startDate || !value || isAfter(parseISO(value), parseISO(startDate)) || isSameDay(parseISO(value), parseISO(startDate));
      }
    ),
  reason: yup.string().required('Reason is required'),
  notes: yup.string(),
  isHalfDay: yup.boolean(),
  halfDayType: yup.string().when('isHalfDay', {
    is: true,
    then: () => yup.string().required('Please specify if it\'s morning or afternoon'),
  }),
});

type LeaveRequestFormData = yup.InferType<typeof leaveRequestSchema>;

// Helper function to check if two dates are the same day
function isSameDay(date1: Date, date2: Date): boolean {
  return date1.getFullYear() === date2.getFullYear() &&
         date1.getMonth() === date2.getMonth() &&
         date1.getDate() === date2.getDate();
}

export default function LeaveRequest() {
  const queryClient = useQueryClient();
  const navigate = useNavigate();
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [isHalfDay, setIsHalfDay] = useState(false);
  
  const {
    register,
    handleSubmit,
    watch,
    setValue,
    formState: { errors },
  } = useForm<LeaveRequestFormData>({
    resolver: yupResolver(leaveRequestSchema),
    defaultValues: {
      type: '',
      startDate: format(new Date(), 'yyyy-MM-dd'),
      endDate: format(new Date(), 'yyyy-MM-dd'),
      reason: '',
      notes: '',
      isHalfDay: false,
      halfDayType: 'MORNING',
    },
  });

  const startDate = watch('startDate');
  const endDate = watch('endDate');

  // Calculate leave days
  const calculateDays = () => {
    if (!startDate || !endDate) return '0';
    
    const start = new Date(startDate);
    const end = new Date(endDate);
    
    if (isAfter(start, end)) return '0';
    
    if (isHalfDay) {
      return '0.5';
    }
    
    const days = calculateWorkingDays(start, end);
    return days.toString();
  };

  const leaveDays = calculateDays();

  // Handle form submission
  const submitLeaveRequest = async (data: LeaveRequestFormData) => {
    setIsSubmitting(true);
    try {
      // In a real app, this would be an API call
      await new Promise(resolve => setTimeout(resolve, 1000));
      
      // Simulate successful submission
      toast.success('Leave request submitted successfully');
      navigate('/leave');
      
      // Invalidate queries to refetch leave data
      queryClient.invalidateQueries(['leaveRequests']);
    } catch (error) {
      console.error('Error submitting leave request:', error);
      toast.error('Failed to submit leave request');
    } finally {
      setIsSubmitting(false);
    }
  };

  const leaveTypes = [
    { value: 'VACATION', label: 'Vacation' },
    { value: 'SICK', label: 'Sick Leave' },
    { value: 'PERSONAL', label: 'Personal Day' },
    { value: 'UNPAID', label: 'Unpaid Leave' },
    { value: 'OTHER', label: 'Other' },
  ];

  return (
    <div className="space-y-6">
      <div className="pb-5 border-b border-gray-200">
        <h1 className="text-2xl font-semibold text-gray-900">Request Time Off</h1>
        <p className="mt-1 text-sm text-gray-500">Submit a new leave request</p>
      </div>

      <div className="bg-white shadow sm:rounded-lg">
        <div className="px-4 py-5 sm:p-6">
          <h3 className="text-lg font-medium leading-6 text-gray-900">Leave Request Details</h3>
          <p className="mt-1 text-sm text-gray-500">
            Fill in the details of your leave request
          </p>
        </div>
        
        <form onSubmit={handleSubmit(submitLeaveRequest)}>
          <div className="border-t border-gray-200 px-4 py-5 sm:p-6">
            <div className="space-y-6">
              <div>
                <label htmlFor="type" className="block text-sm font-medium text-gray-700">
                  Leave Type <span className="text-red-500">*</span>
                </label>
                <select
                  id="type"
                  {...register('type')}
                  className={`mt-1 block w-full rounded-md border py-2 pl-3 pr-10 text-base focus:border-indigo-500 focus:outline-none focus:ring-indigo-500 sm:text-sm ${
                    errors.type ? 'border-red-300' : 'border-gray-300'
                  }`}
                >
                  <option value="">Select leave type</option>
                  {leaveTypes.map((type) => (
                    <option key={type.value} value={type.value}>
                      {type.label}
                    </option>
                  ))}
                </select>
                {errors.type && (
                  <p className="mt-1 text-sm text-red-600">{errors.type.message}</p>
                )}
              </div>

              <div className="grid grid-cols-1 gap-4 sm:grid-cols-2">
                <div>
                  <label htmlFor="startDate" className="block text-sm font-medium text-gray-700">
                    Start Date <span className="text-red-500">*</span>
                  </label>
                  <div className="mt-1">
                    <input
                      type="date"
                      id="startDate"
                      {...register('startDate')}
                      className={`block w-full rounded-md border shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm px-3 py-2 ${
                        errors.startDate ? 'border-red-300' : 'border-gray-300'
                      }`}
                      min={format(new Date(), 'yyyy-MM-dd')}
                      onChange={(e) => {
                        const newStartDate = e.target.value;
                        setValue('startDate', newStartDate);
                        
                        // If end date is before new start date, update end date
                        if (newStartDate && endDate && new Date(newStartDate) > new Date(endDate)) {
                          setValue('endDate', newStartDate);
                        }
                      }}
                    />
                  </div>
                  {errors.startDate && (
                    <p className="mt-1 text-sm text-red-600">{errors.startDate.message}</p>
                  )}
                </div>

                <div>
                  <label htmlFor="endDate" className="block text-sm font-medium text-gray-700">
                    End Date <span className="text-red-500">*</span>
                  </label>
                  <div className="mt-1">
                    <input
                      type="date"
                      id="endDate"
                      {...register('endDate')}
                      className={`block w-full rounded-md border shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm px-3 py-2 ${
                        errors.endDate ? 'border-red-300' : 'border-gray-300'
                      }`}
                      min={startDate || format(new Date(), 'yyyy-MM-dd')}
                    />
                  </div>
                  {errors.endDate && (
                    <p className="mt-1 text-sm text-red-600">{errors.endDate.message}</p>
                  )}
                </div>
              </div>

              <div>
                <div className="flex items-center">
                  <input
                    id="isHalfDay"
                    type="checkbox"
                    {...register('isHalfDay')}
                    onChange={(e) => {
                      setIsHalfDay(e.target.checked);
                      // Reset half day type when toggling half day
                      if (!e.target.checked) {
                        setValue('halfDayType', 'MORNING');
                      }
                    }}
                    className="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500"
                  />
                  <label htmlFor="isHalfDay" className="ml-2 block text-sm text-gray-700">
                    This is a half-day leave
                  </label>
                </div>
                
                {isHalfDay && (
                  <div className="mt-2 ml-6">
                    <div className="flex space-x-4">
                      <div className="flex items-center">
                        <input
                          id="morning"
                          type="radio"
                          value="MORNING"
                          {...register('halfDayType')}
                          className="h-4 w-4 border-gray-300 text-indigo-600 focus:ring-indigo-500"
                        />
                        <label htmlFor="morning" className="ml-2 block text-sm text-gray-700">
                          Morning (9 AM - 1 PM)
                        </label>
                      </div>
                      <div className="flex items-center">
                        <input
                          id="afternoon"
                          type="radio"
                          value="AFTERNOON"
                          {...register('halfDayType')}
                          className="h-4 w-4 border-gray-300 text-indigo-600 focus:ring-indigo-500"
                        />
                        <label htmlFor="afternoon" className="ml-2 block text-sm text-gray-700">
                          Afternoon (1 PM - 5 PM)
                        </label>
                      </div>
                    </div>
                    {errors.halfDayType && (
                      <p className="mt-1 text-sm text-red-600">{errors.halfDayType.message}</p>
                    )}
                  </div>
                )}
              </div>

              <div>
                <label htmlFor="reason" className="block text-sm font-medium text-gray-700">
                  Reason <span className="text-red-500">*</span>
                </label>
                <div className="mt-1">
                  <textarea
                    id="reason"
                    rows={3}
                    {...register('reason')}
                    className={`block w-full rounded-md border shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm px-3 py-2 ${
                      errors.reason ? 'border-red-300' : 'border-gray-300'
                    }`}
                    placeholder="Please provide a reason for your leave"
                  />
                </div>
                {errors.reason && (
                  <p className="mt-1 text-sm text-red-600">{errors.reason.message}</p>
                )}
              </div>

              <div>
                <label htmlFor="notes" className="block text-sm font-medium text-gray-700">
                  Additional Notes (Optional)
                </label>
                <div className="mt-1">
                  <textarea
                    id="notes"
                    rows={3}
                    {...register('notes')}
                    className="block w-full rounded-md border border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm px-3 py-2"
                    placeholder="Any additional information or instructions"
                  />
                </div>
              </div>
            </div>
          </div>

          <div className="bg-gray-50 px-4 py-4 sm:px-6 border-t border-gray-200">
            <div className="flex flex-col sm:flex-row sm:justify-between sm:items-center gap-3">
              <div className="text-sm text-gray-700">
                <span className="font-medium">Total Days:</span> {leaveDays} day{leaveDays !== '1' ? 's' : ''}
              </div>
              <div className="flex space-x-3">
                <button
                  type="button"
                  onClick={() => navigate('/leave')}
                  className="rounded-md border border-gray-300 bg-white py-2 px-4 text-sm font-medium text-gray-700 shadow-sm hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"
                >
                  Cancel
                </button>
                <button
                  type="submit"
                  disabled={isSubmitting}
                  className="inline-flex justify-center rounded-md border border-transparent bg-indigo-600 py-2 px-4 text-sm font-medium text-white shadow-sm hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2 disabled:opacity-50 disabled:cursor-not-allowed"
                >
                  {isSubmitting ? 'Submitting...' : 'Submit Request'}
                </button>
              </div>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
}

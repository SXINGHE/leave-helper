import { useState, useEffect } from 'react';
import type { Ticket } from '../../types/ticket';
import { ticketService } from '../../api/ticketService';
import useAuthStore from '../../stores/authStore';
import { format } from 'date-fns';

interface TicketListProps {
  showAllPending?: boolean;
}

const statusColors = {
  PENDING: 'bg-yellow-100 text-yellow-800',
  APPROVED: 'bg-green-100 text-green-800',
  REJECTED: 'bg-red-100 text-red-800',
};

export default function TicketList({ showAllPending = false }: TicketListProps) {
  const [tickets, setTickets] = useState<Ticket[]>([]);
  const [loading, setLoading] = useState(true);
  const { user } = useAuthStore();
  const isAdmin = user?.role === 'ADMIN';
  const shouldShowAllPending = showAllPending && isAdmin;

  useEffect(() => {
    const fetchTickets = async () => {
      try {
        const data = shouldShowAllPending
          ? await ticketService.getPendingTickets()
          : await ticketService.getMyTickets();
        setTickets(data);
      } catch (error) {
        console.error('Error fetching tickets:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchTickets();
  }, [shouldShowAllPending]);

  const handleApprove = async (id: number) => {
    try {
      const updatedTicket = await ticketService.approveTicket(id);
      setTickets(tickets.map(t => t.id === id ? updatedTicket : t));
    } catch (error) {
      console.error('Error approving ticket:', error);
    }
  };

  const handleReject = async (id: number) => {
    const comments = prompt('Please enter rejection reason:');
    if (comments === null) return;
    
    try {
      const updatedTicket = await ticketService.rejectTicket(id, comments);
      setTickets(tickets.map(t => t.id === id ? updatedTicket : t));
    } catch (error) {
      console.error('Error rejecting ticket:', error);
    }
  };

  if (loading) {
    return <div className="flex justify-center items-center h-64">Loading...</div>;
  }

  return (
    <div className="bg-white shadow overflow-hidden sm:rounded-lg">
      <div className="px-4 py-5 sm:px-6">
        <h3 className="text-lg leading-6 font-medium text-gray-900">
          {isAdmin ? 'Pending Tickets' : 'My Tickets'}
        </h3>
      </div>
      <div className="border-t border-gray-200">
        <ul className="divide-y divide-gray-200">
          {tickets.length === 0 ? (
            <li className="px-4 py-4">
              <p className="text-sm text-gray-500">No tickets found</p>
            </li>
          ) : (
            tickets.map((ticket) => (
              <li key={ticket.id} className="px-4 py-4">
                <div className="flex items-center justify-between">
                  <div className="flex-1">
                    <div className="flex items-center">
                      <p className="text-sm font-medium text-indigo-600 truncate">
                        {ticket.title}
                      </p>
                      <span
                        className={`ml-2 px-2 inline-flex text-xs leading-5 font-semibold rounded-full ${statusColors[ticket.status as keyof typeof statusColors]}`}
                      >
                        {ticket.status}
                      </span>
                    </div>
                    <p className="mt-1 text-sm text-gray-500">{ticket.description}</p>
                    <div className="mt-2 text-xs text-gray-500">
                      Created by {ticket.createdBy} on {format(new Date(ticket.createdAt), 'PPpp')}
                    </div>
                    {ticket.comments && (
                      <div className="mt-2 text-xs text-gray-500">
                        <span className="font-medium">Comments:</span> {ticket.comments}
                      </div>
                    )}
                  </div>
                  {isAdmin && ticket.status === 'PENDING' && (
                    <div className="ml-4 flex-shrink-0 flex space-x-2">
                      <button
                        onClick={() => handleApprove(ticket.id)}
                        className="px-3 py-1 text-sm text-white bg-green-600 hover:bg-green-700 rounded-md"
                      >
                        Approve
                      </button>
                      <button
                        onClick={() => handleReject(ticket.id)}
                        className="px-3 py-1 text-sm text-white bg-red-600 hover:bg-red-700 rounded-md"
                      >
                        Reject
                      </button>
                    </div>
                  )}
                </div>
              </li>
            ))
          )}
        </ul>
      </div>
    </div>
  );
}

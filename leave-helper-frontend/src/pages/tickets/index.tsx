import { Link, Outlet, useLocation } from 'react-router-dom';
import useAuthStore from '../../stores/authStore';

function classNames(...classes: string[]) {
  return classes.filter(Boolean).join(' ');
}

export default function TicketsPage() {
  const location = useLocation();
  const { user } = useAuthStore();
  const isAdmin = user?.role === 'ADMIN';
  const isCreatePage = location.pathname === '/tickets/create';

  const tabs = [
    { name: 'My Tickets', href: '/tickets', current: !isCreatePage },
    { name: 'Create Ticket', href: '/tickets/create', current: isCreatePage },
    ...(isAdmin ? [{ name: 'Pending Approvals', href: '/tickets/pending', current: false }] : []),
  ].filter(Boolean);

  return (
    <div className="py-6">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="mb-6">
          <h1 className="text-2xl font-semibold text-gray-900">Ticket System</h1>
        </div>
        
        <div className="border-b border-gray-200 mb-6">
          <nav className="-mb-px flex space-x-8">
            {tabs.map((tab) => (
              <Link
                key={tab.name}
                to={tab.href}
                className={classNames(
                  tab.current
                    ? 'border-indigo-500 text-indigo-600'
                    : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300',
                  'whitespace-nowrap py-4 px-1 border-b-2 font-medium text-sm'
                )}
                aria-current={tab.current ? 'page' : undefined}
              >
                {tab.name}
              </Link>
            ))}
          </nav>
        </div>

        <div className="bg-white shadow overflow-hidden sm:rounded-lg">
          <Outlet />
        </div>
      </div>
    </div>
  );
}

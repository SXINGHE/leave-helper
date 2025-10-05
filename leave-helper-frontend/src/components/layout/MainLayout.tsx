import { Outlet } from 'react-router-dom';
import { Fragment, useState } from 'react';
import { Dialog, Menu, Transition } from '@headlessui/react';
import {
  Bars3Icon,
  ChartBarIcon,
  HomeIcon,
  UserCircleIcon,
  TicketIcon,
} from '@heroicons/react/24/outline';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import useAuthStore from '../../stores/authStore';
import { toast } from 'react-hot-toast';

const navigation = [
  { name: 'Dashboard', href: '/', icon: HomeIcon },
  { name: 'Tickets', href: '/tickets', icon: TicketIcon },
  { name: 'Reports', href: '/reports', icon: ChartBarIcon },
];

function classNames(...classes: string[]) {
  return classes.filter(Boolean).join(' ');
}

export default function MainLayout() {
  const [sidebarOpen, setSidebarOpen] = useState(false);
  const location = useLocation();
  const navigate = useNavigate();
  const { user, logout } = useAuthStore();

  const handleSignOut = async () => {
    try {
      await logout();
      toast.success('Successfully signed out');
      navigate('/login');
    } catch (error) {
      console.error('Error signing out:', error);
      toast.error('Failed to sign out. Please try again.');
    }
  };

  const isActive = (path: string) => {
    return location.pathname === path || 
           (path !== '/' && location.pathname.startsWith(path));
  };

  return (
    <div className="h-screen flex overflow-hidden bg-gray-100">
      {/* Mobile sidebar */}
      <Transition.Root show={sidebarOpen} as={Fragment}>
        <Dialog as="div" className="fixed inset-0 z-40 lg:hidden" onClose={setSidebarOpen}>
          <Transition.Child
            as={Fragment}
            enter="transition-opacity ease-linear duration-300"
            enterFrom="opacity-0"
            enterTo="opacity-100"
            leave="transition-opacity ease-linear duration-300"
            leaveFrom="opacity-100"
            leaveTo="opacity-0"
          >
            <div className="fixed inset-0 bg-gray-600 bg-opacity-75" />
          </Transition.Child>

          <div className="fixed inset-0 z-40 flex">
            <Transition.Child
              as={Fragment}
              enter="transition ease-in-out duration-300 transform"
              enterFrom="-translate-x-full"
              enterTo="translate-x-0"
              leave="transition ease-in-out duration-300 transform"
              leaveFrom="translate-x-0"
              leaveTo="-translate-x-full"
            >
              <Dialog.Panel className="relative flex w-full max-w-xs flex-1 flex-col bg-white">
                <Transition.Child
                  as={Fragment}
                  enter="ease-in-out duration-300"
                  enterFrom="opacity-0"
                  enterTo="opacity-100"
                  leave="ease-in-out duration-300"
                  leaveFrom="opacity-100"
                  leaveTo="opacity-0"
                >
                  <div className="absolute right-0 top-0 -mr-12 pt-2">
                    <button
                      type="button"
                      className="ml-1 flex h-10 w-10 items-center justify-center rounded-full focus:outline-none focus:ring-2 focus:ring-inset focus:ring-white"
                      onClick={() => setSidebarOpen(false)}
                    >
                      <span className="sr-only">Close sidebar</span>
                      <Bars3Icon className="h-6 w-6 text-white" aria-hidden="true" />
                    </button>
                  </div>
                </Transition.Child>
                <div className="h-0 flex-1 overflow-y-auto pt-5 pb-4">
                  <div className="flex flex-shrink-0 items-center px-4">
                    <h1 className="text-xl font-bold text-indigo-600">Leave Helper</h1>
                  </div>
                  <nav className="mt-5 space-y-1 px-2">
                    {navigation.map((item) => (
                      <Link
                        key={item.name}
                        to={item.href}
                        className={classNames(
                          isActive(item.href)
                            ? 'bg-gray-100 text-indigo-600'
                            : 'text-gray-600 hover:bg-gray-50 hover:text-indigo-600',
                          'group flex items-center px-2 py-2 text-base font-medium rounded-md'
                        )}
                        onClick={() => setSidebarOpen(false)}
                      >
                        <item.icon
                          className={classNames(
                            isActive(item.href) ? 'text-indigo-500' : 'text-gray-400 group-hover:text-indigo-500',
                            'mr-4 h-6 w-6 flex-shrink-0'
                          )}
                          aria-hidden="true"
                        />
                        {item.name}
                      </Link>
                    ))}
                  </nav>
                </div>
                <div className="flex flex-shrink-0 border-t border-gray-200 p-4">
                  <div className="group block w-full flex-shrink-0">
                    <div className="flex items-center">
                      <div>
                        <UserCircleIcon className="h-10 w-10 text-gray-400" aria-hidden="true" />
                      </div>
                      <div className="ml-3">
                        <p className="text-base font-medium text-gray-700 group-hover:text-gray-900">
                          {user?.username}
                        </p>
                        <button
                          onClick={handleSignOut}
                          className="text-sm font-medium text-gray-500 group-hover:text-gray-700"
                        >
                          Sign out
                        </button>
                      </div>
                    </div>
                  </div>
                </div>
              </Dialog.Panel>
            </Transition.Child>
            <div className="w-14 flex-shrink-0">
              {/* Force sidebar to shrink to fit close icon */}
            </div>
          </div>
        </Dialog>
      </Transition.Root>

      {/* Static sidebar for desktop */}
      <div className="hidden lg:flex lg:flex-shrink-0">
        <div className="flex flex-col w-64">
          <div className="flex min-h-0 flex-1 flex-col border-r border-gray-200 bg-white">
            <div className="flex h-16 flex-shrink-0 items-center px-4">
              <h1 className="text-xl font-bold text-indigo-600">Leave Helper</h1>
            </div>
            <div className="flex flex-1 flex-col overflow-y-auto">
              <nav className="flex-1 space-y-1 bg-white px-2 py-4">
                {navigation.map((item) => (
                  <Link
                    key={item.name}
                    to={item.href}
                    className={classNames(
                      isActive(item.href)
                        ? 'bg-gray-100 text-indigo-600'
                        : 'text-gray-600 hover:bg-gray-50 hover:text-indigo-600',
                      'group flex items-center rounded-md px-2 py-2 text-sm font-medium'
                    )}
                  >
                    <item.icon
                      className={classNames(
                        isActive(item.href) ? 'text-indigo-500' : 'text-gray-400 group-hover:text-indigo-500',
                        'mr-3 h-6 w-6 flex-shrink-0'
                      )}
                      aria-hidden="true"
                    />
                    {item.name}
                  </Link>
                ))}
              </nav>
            </div>
            <div className="flex flex-shrink-0 border-t border-gray-200 p-4">
              <div className="group block w-full">
                <div className="flex items-center">
                  <UserCircleIcon className="h-9 w-9 text-gray-400" aria-hidden="true" />
                  <div className="ml-3">
                    <p className="text-sm font-medium text-gray-700">{user?.username}</p>
                    <button
                      onClick={handleSignOut}
                      className="text-xs font-medium text-gray-500 hover:text-gray-700"
                    >
                      Sign out
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* Main content */}
      <div className="flex flex-1 flex-col overflow-hidden">
        {/* Top navigation */}
        <div className="sticky top-0 z-10 flex h-16 bg-white shadow">
          <div className="flex-1 px-4 flex justify-between">
            <div className="flex items-center">
              <button
                type="button"
                className="lg:hidden -ml-0.5 -mt-0.5 h-12 w-12 inline-flex items-center justify-center rounded-md text-gray-500 hover:text-gray-900 focus:outline-none focus:ring-2 focus:ring-inset focus:ring-indigo-500"
                onClick={() => setSidebarOpen(true)}
              >
                <Bars3Icon className="h-6 w-6" aria-hidden="true" />
              </button>
              <div className="ml-4 flex items-center lg:hidden">
                <h1 className="text-lg font-medium text-indigo-600">Leave Helper</h1>
              </div>
            </div>
            <div className="ml-4 flex items-center">
              {/* Profile dropdown */}
              <Menu as="div" className="relative ml-3">
                <div>
                  <Menu.Button className="flex max-w-xs items-center rounded-full bg-white text-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2">
                    <span className="sr-only">Open user menu</span>
                    <UserCircleIcon className="h-8 w-8 text-gray-400" />
                  </Menu.Button>
                </div>
                <Transition
                  as={Fragment}
                  enter="transition ease-out duration-100"
                  enterFrom="transform opacity-0 scale-95"
                  enterTo="transform opacity-100 scale-100"
                  leave="transition ease-in duration-75"
                  leaveFrom="transform opacity-100 scale-100"
                  leaveTo="transform opacity-0 scale-95"
                >
                  <Menu.Items className="absolute right-0 z-10 mt-2 w-48 origin-top-right rounded-md bg-white py-1 shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none">
                    <Menu.Item>
                      {({ active }) => (
                        <button
                          onClick={handleSignOut}
                          className={classNames(
                            active ? 'bg-gray-100' : '',
                            'block w-full px-4 py-2 text-left text-sm text-gray-700'
                          )}
                        >
                          Sign out
                        </button>
                      )}
                    </Menu.Item>
                  </Menu.Items>
                </Transition>
              </Menu>
            </div>
          </div>
        </div>

        {/* Main content area */}
        <main className="flex-1 overflow-y-auto bg-gray-50">
          <div className="py-6">
            <div className="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
              <Outlet />
            </div>
          </div>
        </main>
      </div>
    </div>
  );
}


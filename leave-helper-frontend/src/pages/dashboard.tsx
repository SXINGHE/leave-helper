import { Fragment } from 'react';
import { ChartBarIcon, DocumentTextIcon } from '@heroicons/react/24/outline';

const stats = [
  { id: 1, name: 'Total Reports', value: '24', icon: DocumentTextIcon },
  { id: 2, name: 'Active Projects', value: '5', icon: ChartBarIcon },
];

const recentActivity = [
  { id: 1, user: 'System', action: 'Database backup completed', time: '2 hours ago' },
  { id: 2, user: 'System', action: 'Scheduled maintenance completed', time: '1 day ago' },
];

export default function Dashboard() {
  return (
    <div className="space-y-6">
      <div className="pb-5 border-b border-gray-200">
        <h1 className="text-2xl font-semibold text-gray-900">Dashboard</h1>
      </div>

      {/* Stats */}
      <div className="grid grid-cols-1 gap-5 sm:grid-cols-2 lg:grid-cols-2">
        {stats.map((stat) => (
          <div
            key={stat.id}
            className="bg-white overflow-hidden shadow rounded-lg"
          >
            <div className="p-5">
              <div className="flex items-center">
                <div className="flex-shrink-0">
                  <stat.icon className="h-6 w-6 text-gray-400" aria-hidden="true" />
                </div>
                <div className="ml-5 w-0 flex-1">
                  <dl>
                    <dt className="text-sm font-medium text-gray-500 truncate">{stat.name}</dt>
                    <dd className="flex items-baseline">
                      <div className="text-2xl font-semibold text-gray-900">{stat.value}</div>
                    </dd>
                  </dl>
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

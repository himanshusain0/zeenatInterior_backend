'use client';
import React, { useState, useEffect } from 'react';
import { useRouter } from 'next/navigation';

interface Query {
  id: number;
  name: string;
  email: string;
  phone: string;
  message: string;
  status: string;
  createdAt: string;
}

interface Service {
  id: string;
  title: string;
  description: string;
  icon: string;
}

const AdminDashboard = () => {
  const router = useRouter();
  const [activeTab, setActiveTab] = useState('content');
  const [queries, setQueries] = useState<Query[]>([]);
  const [services, setServices] = useState<Service[]>([]);
  
  const [content, setContent] = useState({
    navbarLogo: '',
    aboutText: '',
    contactInfo: {
      address: '',
      phone: '',
      email: ''
    }
  });

  const [newService, setNewService] = useState({
    title: '',
    description: '',
    icon: ''
  });

  useEffect(() => {
    const token = localStorage.getItem('adminToken');
    if (!token) {
      router.push('/admin/login');
      return;
    }
    loadInitialData();
  }, [router]);

  const loadInitialData = async () => {
    await fetchQueries();
    await fetchServices();
  };

  const fetchQueries = async () => {
    try {
      const token = localStorage.getItem('adminToken');
      const response = await fetch('http://localhost:8080/api/admin/queries', {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });
      
      if (response.ok) {
        const data = await response.json();
        setQueries(data);
      }
    } catch (error) {
      console.error('Error fetching queries:', error);
    }
  };

  const fetchServices = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/admin/services');
      if (response.ok) {
        const data = await response.json();
        setServices(data);
      }
    } catch (error) {
      console.error('Error fetching services:', error);
    }
  };

  const updateContent = async (section: string, data: any) => {
    try {
      const token = localStorage.getItem('adminToken');
      const response = await fetch('http://localhost:8080/api/admin/content', {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify({ section, data })
      });
      
      if (response.ok) {
        alert('Content updated successfully!');
      } else {
        alert('Error updating content');
      }
    } catch (error) {
      alert('Error updating content');
    }
  };

  const deleteQuery = async (id: number) => {
    try {
      const token = localStorage.getItem('adminToken');
      const response = await fetch(`http://localhost:8080/api/admin/queries/${id}`, {
        method: 'DELETE',
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });
      
      if (response.ok) {
        alert('Query deleted successfully!');
        fetchQueries();
      } else {
        alert('Error deleting query');
      }
    } catch (error) {
      alert('Error deleting query');
    }
  };

  const addService = async () => {
    try {
      const token = localStorage.getItem('adminToken');
      const response = await fetch('http://localhost:8080/api/admin/services', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(newService)
      });
      
      if (response.ok) {
        alert('Service added successfully!');
        setNewService({ title: '', description: '', icon: '' });
        fetchServices();
      } else {
        alert('Error adding service');
      }
    } catch (error) {
      alert('Error adding service');
    }
  };

  const deleteService = async (id: string) => {
    try {
      const token = localStorage.getItem('adminToken');
      const response = await fetch(`http://localhost:8080/api/admin/services/${id}`, {
        method: 'DELETE',
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });
      
      if (response.ok) {
        alert('Service deleted successfully!');
        fetchServices();
      } else {
        alert('Error deleting service');
      }
    } catch (error) {
      alert('Error deleting service');
    }
  };

  const handleLogout = () => {
    localStorage.removeItem('adminToken');
    router.push('/admin/login');
  };

  return (
    <div className="min-h-screen bg-gray-100 p-6">
      <div className="max-w-7xl mx-auto">
        {/* Header */}
        <div className="flex justify-between items-center mb-8">
          <h1 className="text-3xl font-bold text-gray-800">Admin Dashboard</h1>
          <button 
            onClick={handleLogout}
            className="bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600"
          >
            Logout
          </button>
        </div>

        {/* Navigation Tabs */}
        <div className="bg-white rounded-lg shadow-md p-6 mb-6">
          <div className="flex space-x-4 border-b">
            {['content', 'queries', 'services'].map((tab) => (
              <button 
                key={tab}
                onClick={() => setActiveTab(tab)}
                className={`pb-2 px-4 capitalize ${
                  activeTab === tab 
                    ? 'border-b-2 border-blue-500 text-blue-500' 
                    : 'text-gray-600 hover:text-blue-500'
                }`}
              >
                {tab === 'queries' ? 'Contact Queries' : 
                 tab === 'services' ? 'Services Management' : 'Content Management'}
              </button>
            ))}
          </div>
        </div>

        {/* Content Management */}
        {activeTab === 'content' && (
          <div className="grid grid-cols-1 gap-6">
            {/* Navbar Logo */}
            <div className="bg-white p-6 rounded-lg shadow-md">
              <h3 className="text-xl font-semibold mb-4">Update Navbar Logo</h3>
              <div className="flex gap-4">
                <input 
                  type="text" 
                  placeholder="Enter logo image URL"
                  className="flex-1 p-2 border border-gray-300 rounded"
                  value={content.navbarLogo}
                  onChange={(e) => setContent({...content, navbarLogo: e.target.value})}
                />
                <button 
                  onClick={() => updateContent('navbarLogo', content.navbarLogo)}
                  className="bg-blue-500 text-white px-6 py-2 rounded hover:bg-blue-600"
                >
                  Update Logo
                </button>
              </div>
            </div>

            {/* About Us */}
            <div className="bg-white p-6 rounded-lg shadow-md">
              <h3 className="text-xl font-semibold mb-4">Update About Us Content</h3>
              <textarea 
                className="w-full p-3 border border-gray-300 rounded mb-4"
                rows={6}
                placeholder="Enter about us content..."
                value={content.aboutText}
                onChange={(e) => setContent({...content, aboutText: e.target.value})}
              />
              <button 
                onClick={() => updateContent('aboutText', content.aboutText)}
                className="bg-blue-500 text-white px-6 py-2 rounded hover:bg-blue-600"
              >
                Update About Us
              </button>
            </div>

            {/* Contact Info */}
            <div className="bg-white p-6 rounded-lg shadow-md">
              <h3 className="text-xl font-semibold mb-4">Update Contact Information</h3>
              <div className="space-y-4">
                <input 
                  type="text"
                  placeholder="Address"
                  className="w-full p-2 border border-gray-300 rounded"
                  value={content.contactInfo.address}
                  onChange={(e) => setContent({
                    ...content, 
                    contactInfo: {...content.contactInfo, address: e.target.value}
                  })}
                />
                <input 
                  type="text"
                  placeholder="Phone"
                  className="w-full p-2 border border-gray-300 rounded"
                  value={content.contactInfo.phone}
                  onChange={(e) => setContent({
                    ...content, 
                    contactInfo: {...content.contactInfo, phone: e.target.value}
                  })}
                />
                <input 
                  type="email"
                  placeholder="Email"
                  className="w-full p-2 border border-gray-300 rounded"
                  value={content.contactInfo.email}
                  onChange={(e) => setContent({
                    ...content, 
                    contactInfo: {...content.contactInfo, email: e.target.value}
                  })}
                />
              </div>
              <button 
                onClick={() => updateContent('contactInfo', content.contactInfo)}
                className="mt-4 bg-blue-500 text-white px-6 py-2 rounded hover:bg-blue-600"
              >
                Update Contact Info
              </button>
            </div>
          </div>
        )}

        {/* Contact Queries */}
        {activeTab === 'queries' && (
          <div className="bg-white p-6 rounded-lg shadow-md">
            <h3 className="text-xl font-semibold mb-4">Contact Queries ({queries.length})</h3>
            <div className="space-y-4">
              {queries.map((query) => (
                <div key={query.id} className="border rounded-lg p-4">
                  <div className="flex justify-between items-start">
                    <div className="flex-1">
                      <h4 className="font-semibold">{query.name}</h4>
                      <p className="text-gray-600">{query.email} | {query.phone}</p>
                      <p className="mt-2 text-gray-800">{query.message}</p>
                      <p className="text-sm text-gray-500 mt-2">
                        {new Date(query.createdAt).toLocaleString()}
                      </p>
                    </div>
                    <button 
                      onClick={() => deleteQuery(query.id)}
                      className="bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600 ml-4"
                    >
                      Delete
                    </button>
                  </div>
                </div>
              ))}
              {queries.length === 0 && (
                <p className="text-gray-500 text-center py-8">No queries found</p>
              )}
            </div>
          </div>
        )}

        {/* Services Management */}
        {activeTab === 'services' && (
          <div className="space-y-6">
            {/* Add Service Form */}
            <div className="bg-white p-6 rounded-lg shadow-md">
              <h3 className="text-xl font-semibold mb-4">Add New Service</h3>
              <div className="grid grid-cols-1 md:grid-cols-3 gap-4 mb-4">
                <input 
                  type="text"
                  placeholder="Service Title"
                  className="p-2 border border-gray-300 rounded"
                  value={newService.title}
                  onChange={(e) => setNewService({...newService, title: e.target.value})}
                />
                <input 
                  type="text"
                  placeholder="Service Description"
                  className="p-2 border border-gray-300 rounded"
                  value={newService.description}
                  onChange={(e) => setNewService({...newService, description: e.target.value})}
                />
                <input 
                  type="text"
                  placeholder="Icon URL or Name"
                  className="p-2 border border-gray-300 rounded"
                  value={newService.icon}
                  onChange={(e) => setNewService({...newService, icon: e.target.value})}
                />
              </div>
              <button 
                onClick={addService}
                className="bg-green-500 text-white px-6 py-2 rounded hover:bg-green-600"
              >
                Add Service
              </button>
            </div>

            {/* Services List */}
            <div className="bg-white p-6 rounded-lg shadow-md">
              <h3 className="text-xl font-semibold mb-4">Current Services ({services.length})</h3>
              <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                {services.map((service) => (
                  <div key={service.id} className="border rounded-lg p-4">
                    <div className="flex justify-between items-start">
                      <div className="flex-1">
                        <h4 className="font-semibold text-lg">{service.title}</h4>
                        <p className="text-gray-600 mt-2">{service.description}</p>
                        <p className="text-sm text-gray-500 mt-2">Icon: {service.icon}</p>
                      </div>
                      <button 
                        onClick={() => deleteService(service.id)}
                        className="bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600 ml-4"
                      >
                        Delete
                      </button>
                    </div>
                  </div>
                ))}
                {services.length === 0 && (
                  <p className="text-gray-500 text-center py-8 col-span-3">No services added yet</p>
                )}
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default AdminDashboard;
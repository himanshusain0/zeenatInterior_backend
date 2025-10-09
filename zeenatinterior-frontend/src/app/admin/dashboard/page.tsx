// /app/admin/dashboard/page.tsx
'use client';
import React, { useState, useEffect } from 'react';
import { useRouter } from 'next/navigation';
import axios from 'axios';

const AdminDashboard = () => {
  const router = useRouter();
  const [content, setContent] = useState({
    navbarLogo: '',
    aboutText: '',
    services: [],
    contactInfo: {}
  });

  useEffect(() => {
    // Check if admin is logged in
    const token = localStorage.getItem('adminToken');
    if (!token) {
      router.push('/admin/login');
    }
  }, [router]);

  const updateContent = async (section: string, newData: any) => {
    try {
      const token = localStorage.getItem('adminToken');
      await axios.put('http://localhost:8080/admin/update-content', 
        { section, data: newData },
        { headers: { Authorization: `Bearer ${token}` }}
      );
      alert('Content updated successfully!');
    } catch (error) {
      alert('Error updating content');
    }
  };

  const handleLogout = () => {
    localStorage.removeItem('adminToken');
    router.push('/admin/login');
  };

  return (
    <div className="min-h-screen bg-gray-100 p-6">
      <div className="max-w-6xl mx-auto">
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
            <button className="pb-2 px-4 border-b-2 border-blue-500 text-blue-500">
              Content Management
            </button>
            <button className="pb-2 px-4 text-gray-600 hover:text-blue-500">
              Contact Queries
            </button>
            <button className="pb-2 px-4 text-gray-600 hover:text-blue-500">
              Portfolio Management
            </button>
          </div>
        </div>

        {/* Content Management Sections */}
        <div className="grid grid-cols-1 gap-6">
          
          {/* Navbar Logo Update */}
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

          {/* About Us Update */}
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

          {/* Services Management */}
          <div className="bg-white p-6 rounded-lg shadow-md">
            <h3 className="text-xl font-semibold mb-4">Manage Services</h3>
            <div className="mb-4">
              <button className="bg-green-500 text-white px-4 py-2 rounded hover:bg-green-600">
                Add New Service
              </button>
            </div>
            {/* Services list will go here */}
          </div>

        </div>
      </div>
    </div>
  );
};

export default AdminDashboard;
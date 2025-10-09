// /app/admin/queries/page.tsx - Add karein
'use client';
import React, { useState, useEffect } from 'react';
import axios from 'axios';

const ContactQueries = () => {
  const [queries, setQueries] = useState([]);

  useEffect(() => {
    fetchQueries();
  }, []);

  const fetchQueries = async () => {
    try {
      const token = localStorage.getItem('adminToken');
      const response = await axios.get('http://localhost:8080/admin/contact-queries', {
        headers: { Authorization: `Bearer ${token}` }
      });
      setQueries(response.data);
    } catch (error) {
      console.error('Error fetching queries');
    }
  };

  const deleteQuery = async (id: string) => {
    try {
      const token = localStorage.getItem('adminToken');
      await axios.delete(`http://localhost:8080/admin/contact-queries/${id}`, {
        headers: { Authorization: `Bearer ${token}` }
      });
      fetchQueries(); // Refresh list
      alert('Query deleted successfully!');
    } catch (error) {
      alert('Error deleting query');
    }
  };

  return (
    <div className="min-h-screen bg-gray-100 p-6">
      <div className="max-w-6xl mx-auto">
        <h1 className="text-3xl font-bold mb-6">Contact Queries</h1>
        
        <div className="bg-white rounded-lg shadow-md">
          {queries.map((query: any) => (
            <div key={query.id} className="border-b p-4">
              <div className="flex justify-between items-start">
                <div>
                  <h3 className="font-semibold">{query.name}</h3>
                  <p className="text-gray-600">{query.email}</p>
                  <p className="mt-2">{query.message}</p>
                  <p className="text-sm text-gray-500 mt-2">
                    {new Date(query.createdAt).toLocaleString()}
                  </p>
                </div>
                <button 
                  onClick={() => deleteQuery(query.id)}
                  className="bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600"
                >
                  Delete
                </button>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default ContactQueries;
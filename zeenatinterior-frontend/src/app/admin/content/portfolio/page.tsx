// app/admin/content/portfolio/page.tsx
'use client';
import { useState, useEffect } from 'react';
import { contentService } from '../../../../lib/content-service';

interface Project {
  _id: string;
  title: string;
  description: string;
  image: string;
  category: string;
  client: string;
  date: string;
}

export default function PortfolioManagement() {
  const [projects, setProjects] = useState<Project[]>([]);
  const [showForm, setShowForm] = useState(false);
  const [editingProject, setEditingProject] = useState<Project | null>(null);
  const [formData, setFormData] = useState({
    title: '',
    description: '',
    category: '',
    client: '',
    date: new Date().toISOString().split('T')[0],
    image: null as File | null
  });

  useEffect(() => {
    fetchProjects();
  }, []);

  const fetchProjects = async () => {
    try {
      const response = await contentService.getPortfolio();
      setProjects(response.projects || []);
    } catch (error) {
      console.error('Error fetching projects:', error);
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    const submitData = new FormData();
    submitData.append('title', formData.title);
    submitData.append('description', formData.description);
    submitData.append('category', formData.category);
    submitData.append('client', formData.client);
    submitData.append('date', formData.date);
    if (formData.image) {
      submitData.append('image', formData.image);
    }

    try {
      if (editingProject) {
        await contentService.updateProject(editingProject._id, submitData);
      } else {
        await contentService.addProject(submitData);
      }
      
      fetchProjects();
      resetForm();
      alert(`Project ${editingProject ? 'updated' : 'added'} successfully!`);
    } catch (error) {
      alert('Error saving project');
    }
  };

  const handleDelete = async (projectId: string) => {
    if (confirm('Are you sure you want to delete this project?')) {
      try {
        await contentService.deleteProject(projectId);
        fetchProjects();
        alert('Project deleted successfully!');
      } catch (error) {
        alert('Error deleting project');
      }
    }
  };

  const resetForm = () => {
    setFormData({
      title: '',
      description: '',
      category: '',
      client: '',
      date: new Date().toISOString().split('T')[0],
      image: null
    });
    setEditingProject(null);
    setShowForm(false);
  };

  return (
    <div className="p-6">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold">Portfolio Management</h1>
        <button
          onClick={() => setShowForm(true)}
          className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
        >
          Add New Project
        </button>
      </div>

      {/* Project Form */}
      {showForm && (
        <div className="bg-white p-6 rounded-lg shadow-md mb-6">
          <h2 className="text-xl font-semibold mb-4">
            {editingProject ? 'Edit Project' : 'Add New Project'}
          </h2>
          <form onSubmit={handleSubmit} className="space-y-4">
            <input
              type="text"
              placeholder="Project Title"
              value={formData.title}
              onChange={(e) => setFormData({...formData, title: e.target.value})}
              className="w-full p-2 border rounded"
              required
            />
            <textarea
              placeholder="Project Description"
              value={formData.description}
              onChange={(e) => setFormData({...formData, description: e.target.value})}
              className="w-full p-2 border rounded"
              rows={4}
              required
            />
            <input
              type="text"
              placeholder="Category"
              value={formData.category}
              onChange={(e) => setFormData({...formData, category: e.target.value})}
              className="w-full p-2 border rounded"
              required
            />
            <input
              type="text"
              placeholder="Client Name"
              value={formData.client}
              onChange={(e) => setFormData({...formData, client: e.target.value})}
              className="w-full p-2 border rounded"
            />
            <input
              type="date"
              value={formData.date}
              onChange={(e) => setFormData({...formData, date: e.target.value})}
              className="w-full p-2 border rounded"
            />
            <input
              type="file"
              accept="image/*"
              onChange={(e) => setFormData({...formData, image: e.target.files?.[0] || null})}
              className="w-full p-2 border rounded"
            />
            <div className="flex gap-2">
              <button type="submit" className="bg-green-600 text-white px-4 py-2 rounded">
                {editingProject ? 'Update' : 'Add'} Project
              </button>
              <button type="button" onClick={resetForm} className="bg-gray-500 text-white px-4 py-2 rounded">
                Cancel
              </button>
            </div>
          </form>
        </div>
      )}

      {/* Projects List */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {projects.map((project) => (
          <div key={project._id} className="bg-white rounded-lg shadow-md overflow-hidden">
            <img 
              src={project.image} 
              alt={project.title}
              className="w-full h-48 object-cover"
            />
            <div className="p-4">
              <h3 className="font-semibold text-lg mb-2">{project.title}</h3>
              <p className="text-gray-600 text-sm mb-2">{project.category}</p>
              <p className="text-gray-500 text-xs mb-4">{project.client}</p>
              <div className="flex gap-2">
                <button
                  onClick={() => {
                    setEditingProject(project);
                    setFormData({
                      title: project.title,
                      description: project.description,
                      category: project.category,
                      client: project.client,
                      date: project.date,
                      image: null
                    });
                    setShowForm(true);
                  }}
                  className="bg-blue-500 text-white px-3 py-1 rounded text-sm"
                >
                  Edit
                </button>
                <button
                  onClick={() => handleDelete(project._id)}
                  className="bg-red-500 text-white px-3 py-1 rounded text-sm"
                >
                  Delete
                </button>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
// app/admin/content/about/page.tsx
'use client';
import { useState, useEffect } from 'react';
import { contentService } from '../../../../lib/content-service';

export default function AboutContent() {
  const [formData, setFormData] = useState({
    title: '',
    description: '',
    mission: '',
    vision: '',
    image: null as File | null,
    currentImage: ''
  });
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    fetchAboutContent();
  }, []);

  const fetchAboutContent = async () => {
    try {
      const response = await contentService.getAbout();
      if (response.about) {
        setFormData(prev => ({
          ...prev,
          ...response.about,
          currentImage: response.about.image || ''
        }));
      }
    } catch (error) {
      console.error('Error fetching about content:', error);
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);

    try {
      // ✅ CORRECT: Plain object bhejein jaisa service expect kar raha hai
      await contentService.updateAbout({
        title: formData.title,
        description: formData.description,
        mission: formData.mission,
        vision: formData.vision,
        image: formData.image || undefined
      });
      
      await fetchAboutContent(); // Refresh data
      alert('About page updated successfully!');
    } catch (error) {
      console.error('Update error:', error);
      alert('Error updating about page');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-6">About Page Management</h1>
      
      <form onSubmit={handleSubmit} className="bg-white p-6 rounded-lg shadow-md space-y-4">
        {/* Current Image Display */}
        {formData.currentImage && (
          <div>
            <p className="font-medium mb-2">Current Image:</p>
            <img 
              src={formData.currentImage} 
              alt="About Us" 
              className="h-40 object-cover rounded border"
            />
          </div>
        )}

        <input
          type="text"
          placeholder="Page Title"
          value={formData.title}
          onChange={(e) => setFormData({...formData, title: e.target.value})}
          className="w-full p-3 border rounded"
          required
        />

        <textarea
          placeholder="Description"
          value={formData.description}
          onChange={(e) => setFormData({...formData, description: e.target.value})}
          className="w-full p-3 border rounded"
          rows={6}
          required
        />

        <textarea
          placeholder="Our Mission"
          value={formData.mission}
          onChange={(e) => setFormData({...formData, mission: e.target.value})}
          className="w-full p-3 border rounded"
          rows={4}
          required
        />

        <textarea
          placeholder="Our Vision"
          value={formData.vision}
          onChange={(e) => setFormData({...formData, vision: e.target.value})}
          className="w-full p-3 border rounded"
          rows={4}
          required
        />

        <div>
          <label className="block font-medium mb-2">Update Image:</label>
          <input
            type="file"
            accept="image/*"
            onChange={(e) => setFormData({...formData, image: e.target.files?.[0] || null})}
            className="w-full p-2 border rounded"
          />
        </div>

        <button
          type="submit"
          disabled={loading}
          className="bg-blue-600 text-white px-6 py-3 rounded hover:bg-blue-700 disabled:opacity-50"
        >
          {loading ? 'Updating...' : 'Update About Page'}
        </button>
      </form>
    </div>
  );
}
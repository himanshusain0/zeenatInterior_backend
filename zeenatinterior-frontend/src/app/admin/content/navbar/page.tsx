// app/admin/content/navbar/page.tsx
'use client';
import { useState, useEffect } from 'react';
import { contentService } from '../../../../lib/content-service';

export default function NavbarContent() {
  const [logo, setLogo] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    fetchLogo();
  }, []);

  const fetchLogo = async () => {
    try {
      const response = await contentService.getLogo();
      setLogo(response.logoUrl);
    } catch (error) {
      console.error('Error fetching logo:', error);
    }
  };

  const handleLogoUpload = async (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (!file) return;

    setLoading(true);
    try {
      await contentService.uploadLogo(file);
      await fetchLogo(); // Refresh logo
      alert('Logo updated successfully!');
    } catch (error) {
      alert('Error uploading logo');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-6">Navbar & Logo Management</h1>
      
      <div className="bg-white p-6 rounded-lg shadow-md">
        <h2 className="text-xl font-semibold mb-4">Website Logo</h2>
        
        {/* Current Logo Display */}
        {logo && (
          <div className="mb-4">
            <p className="font-medium mb-2">Current Logo:</p>
            <img 
              src={logo} 
              alt="Website Logo" 
              className="h-20 object-contain border rounded p-2"
            />
          </div>
        )}

        {/* Logo Upload */}
        <div>
          <label className="block text-sm font-medium mb-2">
            Upload New Logo:
          </label>
          <input
            type="file"
            accept="image/*"
            onChange={handleLogoUpload}
            disabled={loading}
            className="block w-full text-sm text-gray-500 file:mr-4 file:py-2 file:px-4 file:rounded-full file:border-0 file:text-sm file:font-semibold file:bg-blue-50 file:text-blue-700 hover:file:bg-blue-100"
          />
          <p className="text-sm text-gray-500 mt-1">
            Recommended: PNG format, transparent background, 200x50px
          </p>
        </div>
      </div>
    </div>
  );
}
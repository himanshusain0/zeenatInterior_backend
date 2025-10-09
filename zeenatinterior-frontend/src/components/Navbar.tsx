// components/Navbar.tsx
'use client';
import { useState, useEffect } from 'react';
import { contentService } from '../lib/content-service';

export default function Navbar() {
  const [logo, setLogo] = useState<string | null>(null);

  useEffect(() => {
    fetchLogo();
  }, []);

  const fetchLogo = async () => {
    try {
      const response = await contentService.getLogo();
      setLogo(response.logoUrl);
    } catch (error) {
      console.error('Error fetching logo:', error);
      // Fallback to default logo
      setLogo('/default-logo.png');
    }
  };

  return (
    <nav className="bg-white shadow-lg">
      <div className="container mx-auto px-4">
        <div className="flex justify-between items-center py-4">
          {/* Dynamic Logo */}
          {logo ? (
            <img 
              src={logo} 
              alt="Zeenat Interior" 
              className="h-12 object-contain"
            />
          ) : (
            <div className="h-12 w-32 bg-gray-200 animate-pulse rounded"></div>
          )}
          
          {/* Navigation Links */}
          <div className="flex space-x-8">
            <a href="/" className="text-gray-700 hover:text-blue-600">Home</a>
            <a href="/about" className="text-gray-700 hover:text-blue-600">About</a>
            <a href="/services" className="text-gray-700 hover:text-blue-600">Services</a>
            <a href="/portfolio" className="text-gray-700 hover:text-blue-600">Portfolio</a>
            <a href="/contact" className="text-gray-700 hover:text-blue-600">Contact</a>
          </div>
        </div>
      </div>
    </nav>
  );
}
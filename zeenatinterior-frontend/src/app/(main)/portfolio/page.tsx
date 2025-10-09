'use client'
import { useState, useEffect } from 'react'
import { contentService } from '../../../lib/content-service'

interface Project {
  id: string;
  title: string;
  description: string;
  image: string;
  category: string;
  client: string;
  projectDate: string;
}

export default function PortfolioPage() {
  const [projects, setProjects] = useState<Project[]>([])
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    fetchProjects()
  }, [])

  const fetchProjects = async () => {
    try {
      const response = await contentService.getPortfolio()
      setProjects(response.projects || [])
    } catch (error) {
      console.error('Error fetching projects:', error)
      // Fallback to static projects if API fails
      setProjects([
        {
          id: '1',
          title: "Modern Living Room",
          description: "Contemporary living room design with minimalist approach",
          image: "/fallback-image.jpg",
          category: "Residential",
          client: "",
          projectDate: "2024-01-01"
        },
        // ... other fallback projects
      ])
    } finally {
      setLoading(false)
    }
  }

  if (loading) {
    return (
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
        <h1 className="text-4xl font-bold text-center mb-12">Our Portfolio</h1>
        <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-8">
          {[1, 2, 3, 4, 5, 6].map((item) => (
            <div key={item} className="bg-white rounded-lg shadow-lg overflow-hidden animate-pulse">
              <div className="h-48 bg-gray-200"></div>
              <div className="p-6">
                <div className="h-4 bg-gray-200 rounded w-1/3 mb-2"></div>
                <div className="h-6 bg-gray-200 rounded w-3/4 mb-2"></div>
                <div className="h-4 bg-gray-200 rounded w-full"></div>
              </div>
            </div>
          ))}
        </div>
      </div>
    )
  }

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
      <h1 className="text-4xl font-bold text-center mb-12">Our Portfolio</h1>
      
      {projects.length === 0 ? (
        <div className="text-center text-gray-500 py-12">
          <p className="text-lg">No projects added yet. Please check back later.</p>
        </div>
      ) : (
        <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-8">
          {projects.map((project) => (
            <div key={project.id} className="bg-white rounded-lg shadow-lg overflow-hidden hover:shadow-xl transition-shadow duration-300">
              {/* Dynamic Image */}
              <div className="h-48 bg-gray-100 overflow-hidden">
                {project.image ? (
                  <img 
                    src={project.image} 
                    alt={project.title}
                    className="w-full h-full object-cover hover:scale-105 transition-transform duration-300"
                  />
                ) : (
                  <div className="w-full h-full bg-gray-200 flex items-center justify-center">
                    <span className="text-gray-500">Project Image</span>
                  </div>
                )}
              </div>
              
              <div className="p-6">
                {/* Dynamic Category */}
                <span className="inline-block bg-indigo-100 text-indigo-800 text-sm px-3 py-1 rounded-full mb-2">
                  {project.category || "Project"}
                </span>
                
                {/* Dynamic Title */}
                <h3 className="text-xl font-semibold mb-2">{project.title}</h3>
                
                {/* Dynamic Description */}
                <p className="text-gray-600 mb-3">{project.description}</p>
                
                {/* Client Name (if available) */}
                {project.client && (
                  <p className="text-sm text-gray-500">
                    <strong>Client:</strong> {project.client}
                  </p>
                )}
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  )
}
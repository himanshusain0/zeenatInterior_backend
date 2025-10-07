'use client'
import { useEffect, useState } from 'react'
import { servicesAPI } from '../../lib/api'

interface Service {
  id: number
  title: string
  description: string
  price: number
  thumbnailUrl: string
}

export default function HomePage() {
  const [services, setServices] = useState<Service[]>([])

  useEffect(() => {
    loadServices()
  }, [])

  const loadServices = async () => {
    try {
      const response = await servicesAPI.getAll()
      setServices(response.data)
    } catch (error) {
      console.error('Failed to load services')
    }
  }

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
      {/* Hero Section */}
      <section className="text-center mb-16">
        <h1 className="text-5xl font-bold text-gray-900 mb-6">
          Transform Your Space with 
          <span className="text-indigo-600"> Zeenat Interior</span>
        </h1>
        <p className="text-xl text-gray-600 max-w-3xl mx-auto">
          Premium interior design services for homes, offices, and commercial spaces. 
          Let us bring your vision to life.
        </p>
      </section>

      {/* Services Section */}
      <section className="mb-16">
        <h2 className="text-3xl font-bold text-center mb-12">Our Services</h2>
        <div className="grid md:grid-cols-3 gap-8">
          {services.map((service) => (
            <div key={service.id} className="bg-white p-6 rounded-lg shadow-md border border-gray-200">
              <h3 className="text-xl font-semibold mb-3">{service.title}</h3>
              <p className="text-gray-600 mb-4">{service.description}</p>
              <p className="text-indigo-600 font-bold text-lg">₹{service.price}</p>
            </div>
          ))}
        </div>
      </section>

      {/* Portfolio Preview */}
      <section className="text-center">
        <h2 className="text-3xl font-bold mb-8">Our Portfolio</h2>
        <p className="text-gray-600 mb-8">
          Explore our latest interior design projects and get inspired for your next transformation.
        </p>
        <a href="/portfolio" className="bg-indigo-600 text-white px-8 py-3 rounded-lg hover:bg-indigo-700">
          View Portfolio
        </a>
      </section>
    </div>
  )
}
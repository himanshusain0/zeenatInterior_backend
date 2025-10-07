'use client'
import { useEffect, useState } from 'react'
import { servicesAPI } from '../../../lib/api'

interface Service {
  id: number
  title: string
  description: string
  price: number
  thumbnailUrl: string
}

export default function ServicesPage() {
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
      <h1 className="text-4xl font-bold text-center mb-12">Our Services</h1>
      
      <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-8">
        {services.map((service) => (
          <div key={service.id} className="bg-white p-6 rounded-lg shadow-lg border border-gray-200">
            <h3 className="text-2xl font-semibold mb-4">{service.title}</h3>
            <p className="text-gray-600 mb-4">{service.description}</p>
            <div className="flex justify-between items-center">
              <span className="text-indigo-600 font-bold text-xl">₹{service.price}</span>
              <button className="bg-indigo-600 text-white px-4 py-2 rounded hover:bg-indigo-700">
                Get Quote
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  )
}
'use client'
import Link from 'next/link'
import { usePathname } from 'next/navigation'
import { authUtils } from '../../lib/auth'
import { useState, useEffect } from 'react'
import { contentService } from '../../lib/content-service'

export default function Header() {
  const pathname = usePathname()
  const user = authUtils.getUser()
  const isAdmin = authUtils.isAdmin()
  const [logo, setLogo] = useState<string | null>(null)

  useEffect(() => {
    fetchLogo()
  }, [])

  const fetchLogo = async () => {
    try {
      const response = await contentService.getLogo()
      setLogo(response.logoUrl)
    } catch (error) {
      console.error('Error fetching logo:', error)
      // Agar logo nahi mila toh existing text hi rahega
    }
  }

  return (
    <header className="bg-white shadow-sm sticky top-0 z-50">
      <nav className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between h-16">
          <div className="flex items-center">
            {/* YAHAN LOGO KO DYNAMIC KARENGE */}
            <Link href="/" className="flex-shrink-0 font-bold text-xl text-gray-800">
              {logo ? (
                <img 
                  src={logo} 
                  alt="Zeenat Interior" 
                  className="h-10 object-contain" // Height adjust karo according to your design
                />
              ) : (
                // Fallback - aapka existing text
                "Zeenat Interior"
              )}
            </Link>
            
            <div className="hidden sm:ml-6 sm:flex sm:space-x-8">
              <Link href="/" className={`px-3 py-2 text-sm font-medium ${pathname === '/' ? 'text-indigo-600' : 'text-gray-700 hover:text-indigo-600'}`}>
                Home
              </Link>
              <Link href="/services" className={`px-3 py-2 text-sm font-medium ${pathname === '/services' ? 'text-indigo-600' : 'text-gray-700 hover:text-indigo-600'}`}>
                Services
              </Link>
              <Link href="/portfolio" className={`px-3 py-2 text-sm font-medium ${pathname === '/portfolio' ? 'text-indigo-600' : 'text-gray-700 hover:text-indigo-600'}`}>
                Portfolio
              </Link>
              <Link href="/about" className={`px-3 py-2 text-sm font-medium ${pathname === '/about' ? 'text-indigo-600' : 'text-gray-700 hover:text-indigo-600'}`}>
                About
              </Link>
              <Link href="/contact" className={`px-3 py-2 text-sm font-medium ${pathname === '/contact' ? 'text-indigo-600' : 'text-gray-700 hover:text-indigo-600'}`}>
                Contact
              </Link>
            </div>
          </div>

          <div className="flex items-center space-x-4">
            {user ? (
              <>
                {isAdmin && (
                  <Link href="/admin/dashboard" className="bg-red-600 text-white px-4 py-2 rounded-md text-sm font-medium">
                    Admin Panel
                  </Link>
                )}
                <Link href="/dashboard" className="text-gray-700 hover:text-indigo-600 px-3 py-2 text-sm font-medium">
                  Dashboard
                </Link>
                <button
                  onClick={() => authUtils.logout()}
                  className="text-gray-700 hover:text-indigo-600 px-3 py-2 text-sm font-medium"
                >
                  Logout
                </button>
              </>
            ) : (
              <>
                <Link href="/login" className="text-gray-700 hover:text-indigo-600 px-3 py-2 text-sm font-medium">
                  Login
                </Link>
                <Link href="/register" className="bg-indigo-600 text-white px-4 py-2 rounded-md text-sm font-medium hover:bg-indigo-700">
                  Sign Up
                </Link>
              </>
            )}
          </div>
        </div>
      </nav>
    </header>
  )
}
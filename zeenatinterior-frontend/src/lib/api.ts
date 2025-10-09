// lib/api.ts
import axios from 'axios'

// ✅ Use .env variable for base URL if available
const API_BASE_URL = process.env.NEXT_PUBLIC_API_BASE_URL || 'http://localhost:8080'

// ✅ Axios instance
const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
})

// ✅ Interceptor — Add token to all requests
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token') || localStorage.getItem('adminToken')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

// ✅ Interceptor — Handle 401 globally
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response && error.response.status === 401) {
      localStorage.removeItem('token')
      localStorage.removeItem('adminToken')
      window.location.href = '/admin' // redirect to admin login
    }
    return Promise.reject(error)
  }
)

// ✅ Auth APIs
export const authAPI = {
  login: (data: { email: string; password: string }) =>
    api.post('/api/auth/login', data),

  register: (data: { name: string; email: string; password: string }) =>
    api.post('/api/auth/register', data),
}

// ✅ Services APIs
export const servicesAPI = {
  getAll: () => api.get('/api/services'),
  create: (data: any) => api.post('/api/services', data),
  update: (id: number, data: any) => api.put(`/api/services/${id}`, data),
  delete: (id: number) => api.delete(`/api/services/${id}`),
}

// ✅ Queries APIs
export const queriesAPI = {
  submit: (data: any) => api.post('/api/queries', data),
  getAll: () => api.get('/api/queries'),
}

// ✅ User Images APIs
export const userImagesAPI = {
  upload: (file: File) => {
    const formData = new FormData()
    formData.append('file', file)
    return api.post('/api/user-images', formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    })
  },
  getAll: () => api.get('/api/user-images'),
  delete: (id: number) => api.delete(`/api/user-images/${id}`),
}

// ✅ Optional: Generic helper methods like fetch version
export const httpAPI = {
  get: (endpoint: string) => api.get(endpoint),
  post: (endpoint: string, data: any) => api.post(endpoint, data),
  put: (endpoint: string, data: any) => api.put(endpoint, data),
  delete: (endpoint: string) => api.delete(endpoint),
}

export default api

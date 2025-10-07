import axios from 'axios'

const API_BASE_URL = 'http://localhost:8080'

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
})

// Add token to all requests
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

// Auth APIs
export const authAPI = {
  login: (data: { email: string; password: string }) => 
    api.post('/api/auth/login', data),
  
  register: (data: { name: string; email: string; password: string }) => 
    api.post('/api/auth/register', data),
}

// Services APIs
export const servicesAPI = {
  getAll: () => api.get('/api/services'),
  create: (data: any) => api.post('/api/services', data),
  update: (id: number, data: any) => api.put(`/api/services/${id}`, data),
  delete: (id: number) => api.delete(`/api/services/${id}`),
}

// Queries APIs
export const queriesAPI = {
  submit: (data: any) => api.post('/api/queries', data),
  getAll: () => api.get('/api/queries'),
}

// User Images APIs
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

export default api
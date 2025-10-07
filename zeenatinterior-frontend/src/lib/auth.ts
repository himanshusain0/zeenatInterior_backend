export const authUtils = {
  setToken: (token: string) => {
    localStorage.setItem('token', token)
  },
  
  getToken: () => {
    return localStorage.getItem('token')
  },
  
  setUser: (user: any) => {
    localStorage.setItem('user', JSON.stringify(user))
  },
  
  getUser: () => {
    if (typeof window === 'undefined') return null
    const user = localStorage.getItem('user')
    return user ? JSON.parse(user) : null
  },
  
  isAdmin: () => {
    const user = authUtils.getUser()
    return user?.role === 'ADMIN'
  },
  
  logout: () => {
    localStorage.removeItem('token')
    localStorage.removeItem('user')
    window.location.href = '/login'
  }
}
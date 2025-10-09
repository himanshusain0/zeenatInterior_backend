// lib/content-service.ts
import  api  from './api';

export const contentService = {
  // Logo Management
  uploadLogo: async (logoFile: File) => {
    const formData = new FormData();
    formData.append('logo', logoFile);
    
    const response = await api.post('/admin/content/logo', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    });
    return response.data;
  },

  getLogo: async () => {
    const response = await api.get('/admin/content/logo');
    return response.data;
  },

  // Portfolio Management
  getPortfolio: async () => {
    const response = await api.get('/admin/content/portfolio');
    return response.data;
  },

  addProject: async (projectData: FormData) => {
    const response = await api.post('/admin/content/portfolio', projectData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    });
    return response.data;
  },

  updateProject: async (id: string, projectData: any) => {
    const response = await api.put(`/admin/content/portfolio/${id}`, projectData);
    return response.data;
  },

  deleteProject: async (id: string) => {
    const response = await api.delete(`/admin/content/portfolio/${id}`);
    return response.data;
  },

  // About Page Management
  updateAbout: async (aboutData: {
    title: string;
    description: string;
    mission: string;
    vision: string;
    image?: File;
  }) => {
    const formData = new FormData();
    formData.append('title', aboutData.title);
    formData.append('description', aboutData.description);
    formData.append('mission', aboutData.mission);
    formData.append('vision', aboutData.vision);
    if (aboutData.image) {
      formData.append('image', aboutData.image);
    }

    const response = await api.post('/admin/content/about', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    });
    return response.data;
  },

  getAbout: async () => {
    const response = await api.get('/admin/content/about');
    return response.data;
  },

  // Contact Page Management
  updateContact: async (contactData: {
    email: string;
    phone: string;
    address: string;
    additionalFields: Array<{
      label: string;
      type: string;
      required: boolean;
    }>;
  }) => {
    const response = await api.post('/admin/content/contact', contactData);
    return response.data;
  },

  getContact: async () => {
    const response = await api.get('/admin/content/contact');
    return response.data;
  }
};
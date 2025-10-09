// app/admin/content/contact/page.tsx
'use client';
import { useState, useEffect } from 'react';
import { contentService } from '../../../../lib/content-service';

interface ContactField {
  label: string;
  type: string;
  required: boolean;
}

export default function ContactContent() {
  const [formData, setFormData] = useState({
    email: '',
    phone: '',
    address: '',
    additionalFields: [] as ContactField[]
  });
  const [newField, setNewField] = useState<ContactField>({
    label: '',
    type: 'text',
    required: false
  });

  useEffect(() => {
    fetchContactContent();
  }, []);

  const fetchContactContent = async () => {
    try {
      const response = await contentService.getContact();
      if (response.contact) {
        setFormData(response.contact);
      }
    } catch (error) {
      console.error('Error fetching contact content:', error);
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    try {
      await contentService.updateContact(formData);
      alert('Contact page updated successfully!');
    } catch (error) {
      alert('Error updating contact page');
    }
  };

  const addField = () => {
    if (newField.label.trim()) {
      setFormData(prev => ({
        ...prev,
        additionalFields: [...prev.additionalFields, { ...newField }]
      }));
      setNewField({ label: '', type: 'text', required: false });
    }
  };

  const removeField = (index: number) => {
    setFormData(prev => ({
      ...prev,
      additionalFields: prev.additionalFields.filter((_, i) => i !== index)
    }));
  };

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-6">Contact Page Management</h1>
      
      <form onSubmit={handleSubmit} className="bg-white p-6 rounded-lg shadow-md space-y-4">
        <input
          type="email"
          placeholder="Contact Email"
          value={formData.email}
          onChange={(e) => setFormData({...formData, email: e.target.value})}
          className="w-full p-3 border rounded"
          required
        />

        <input
          type="text"
          placeholder="Phone Number"
          value={formData.phone}
          onChange={(e) => setFormData({...formData, phone: e.target.value})}
          className="w-full p-3 border rounded"
          required
        />

        <textarea
          placeholder="Address"
          value={formData.address}
          onChange={(e) => setFormData({...formData, address: e.target.value})}
          className="w-full p-3 border rounded"
          rows={3}
          required
        />

        {/* Additional Fields Management */}
        <div className="border-t pt-4">
          <h3 className="text-lg font-semibold mb-3">Additional Contact Fields</h3>
          
          {/* Add New Field */}
          <div className="flex gap-2 mb-4 p-3 bg-gray-50 rounded">
            <input
              type="text"
              placeholder="Field Label (e.g., Company Name)"
              value={newField.label}
              onChange={(e) => setNewField({...newField, label: e.target.value})}
              className="flex-1 p-2 border rounded"
            />
            <select
              value={newField.type}
              onChange={(e) => setNewField({...newField, type: e.target.value})}
              className="p-2 border rounded"
            >
              <option value="text">Text</option>
              <option value="email">Email</option>
              <option value="tel">Phone</option>
              <option value="textarea">Textarea</option>
            </select>
            <label className="flex items-center">
              <input
                type="checkbox"
                checked={newField.required}
                onChange={(e) => setNewField({...newField, required: e.target.checked})}
                className="mr-2"
              />
              Required
            </label>
            <button
              type="button"
              onClick={addField}
              className="bg-green-500 text-white px-3 py-2 rounded"
            >
              Add Field
            </button>
          </div>

          {/* Existing Fields List */}
          {formData.additionalFields.map((field, index) => (
            <div key={index} className="flex items-center gap-2 p-2 border rounded mb-2">
              <span className="flex-1">
                <strong>{field.label}</strong> ({field.type}) {field.required && '*'}
              </span>
              <button
                type="button"
                onClick={() => removeField(index)}
                className="bg-red-500 text-white px-2 py-1 rounded text-sm"
              >
                Remove
              </button>
            </div>
          ))}
        </div>

        <button
          type="submit"
          className="bg-blue-600 text-white px-6 py-3 rounded hover:bg-blue-700"
        >
          Update Contact Page
        </button>
      </form>
    </div>
  );
}
export default function PortfolioPage() {
  const projects = [
    {
      title: "Modern Living Room",
      description: "Contemporary living room design with minimalist approach",
      type: "Residential"
    },
    {
      title: "Office Space Design",
      description: "Corporate office interior with ergonomic furniture",
      type: "Commercial"
    },
    {
      title: "Kitchen Remodeling",
      description: "Complete kitchen transformation with modern appliances",
      type: "Residential"
    },
    {
      title: "Hotel Lobby",
      description: "Luxury hotel lobby design with premium materials",
      type: "Commercial"
    },
    {
      title: "Bedroom Suite",
      description: "Master bedroom with custom wardrobe and lighting",
      type: "Residential"
    },
    {
      title: "Restaurant Interior",
      description: "Fine dining restaurant with ambient lighting",
      type: "Commercial"
    }
  ]

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
      <h1 className="text-4xl font-bold text-center mb-12">Our Portfolio</h1>
      
      <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-8">
        {projects.map((project, index) => (
          <div key={index} className="bg-white rounded-lg shadow-lg overflow-hidden">
            <div className="h-48 bg-gray-200 flex items-center justify-center">
              <span className="text-gray-500">Project Image</span>
            </div>
            <div className="p-6">
              <span className="inline-block bg-indigo-100 text-indigo-800 text-sm px-3 py-1 rounded-full mb-2">
                {project.type}
              </span>
              <h3 className="text-xl font-semibold mb-2">{project.title}</h3>
              <p className="text-gray-600">{project.description}</p>
            </div>
          </div>
        ))}
      </div>
    </div>
  )
}
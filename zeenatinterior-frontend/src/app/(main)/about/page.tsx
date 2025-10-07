export default function AboutPage() {
  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
      <h1 className="text-4xl font-bold text-center mb-12">About Zeenat Interior</h1>
      
      <div className="grid md:grid-cols-2 gap-12 items-center">
        <div>
          <h2 className="text-2xl font-semibold mb-6">Our Story</h2>
          <p className="text-gray-600 mb-4">
            Zeenat Interior has been transforming spaces into beautiful, functional, and inspiring 
            environments for over a decade. Our passion for design and commitment to excellence 
            has made us a trusted name in the interior design industry.
          </p>
          <p className="text-gray-600 mb-4">
            We believe that every space tells a story, and we're here to help you tell yours. 
            From residential homes to commercial spaces, we bring creativity, expertise, and 
            attention to detail to every project.
          </p>
          <p className="text-gray-600">
            Our team of experienced designers and craftsmen work closely with clients to 
            understand their vision and bring it to life with precision and care.
          </p>
        </div>
        
        <div className="bg-gray-100 h-80 rounded-lg flex items-center justify-center">
          <span className="text-gray-500">Company Image</span>
        </div>
      </div>

      <div className="mt-16">
        <h2 className="text-2xl font-semibold mb-8 text-center">Why Choose Us?</h2>
        <div className="grid md:grid-cols-3 gap-8">
          <div className="text-center">
            <div className="bg-indigo-100 w-16 h-16 rounded-full flex items-center justify-center mx-auto mb-4">
              <span className="text-indigo-600 font-bold">10+</span>
            </div>
            <h3 className="text-lg font-semibold mb-2">Years Experience</h3>
            <p className="text-gray-600">Over a decade of design excellence</p>
          </div>
          
          <div className="text-center">
            <div className="bg-indigo-100 w-16 h-16 rounded-full flex items-center justify-center mx-auto mb-4">
              <span className="text-indigo-600 font-bold">500+</span>
            </div>
            <h3 className="text-lg font-semibold mb-2">Projects Completed</h3>
            <p className="text-gray-600">Successful transformations</p>
          </div>
          
          <div className="text-center">
            <div className="bg-indigo-100 w-16 h-16 rounded-full flex items-center justify-center mx-auto mb-4">
              <span className="text-indigo-600 font-bold">100%</span>
            </div>
            <h3 className="text-lg font-semibold mb-2">Client Satisfaction</h3>
            <p className="text-gray-600">Happy customers nationwide</p>
          </div>
        </div>
      </div>
    </div>
  )
}
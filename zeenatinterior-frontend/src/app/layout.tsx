import './globals.css'

export const metadata = {
  title: 'Zeenat Interior',
  description: 'Premium Interior Design Services',
}

export default function RootLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <html lang="en">
      <body className="min-h-screen bg-white">
        {children}
      </body>
    </html>
  )
}
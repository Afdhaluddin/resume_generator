/**
 * Prerender Script
 * 
 * This script generates a static HTML snapshot of the homepage
 * and injects it into the built index.html. This ensures search
 * engines can crawl all content without executing JavaScript.
 * 
 * The Vue app still hydrates client-side for full interactivity.
 */

import fs from 'fs'
import path from 'path'
import { fileURLToPath } from 'url'

const __dirname = path.dirname(fileURLToPath(import.meta.url))
const distPath = path.resolve(__dirname, '../dist')
const indexPath = path.join(distPath, 'index.html')

// Static HTML snapshot of the homepage content
// This mirrors the HomeView.vue component structure
const homePageSnapshot = `
<div>
  <!-- Hero Section -->
  <section class="bg-gradient-to-br from-primary-600 to-primary-800 text-white py-20" aria-label="Hero">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 text-center">
      <h1 class="text-4xl sm:text-5xl lg:text-6xl font-bold mb-6">
        Build Your Professional Resume Online — Free
      </h1>
      <p class="text-xl text-primary-100 mb-8 max-w-2xl mx-auto">
        Create stunning, ATS-friendly resumes in minutes. Choose from professionally designed templates tailored for students, doctors, lawyers, programmers, and business professionals.
      </p>
      <div class="flex justify-center gap-4">
        <a href="#templates" class="bg-white text-primary-700 px-8 py-3 rounded-xl font-semibold hover:bg-primary-50 transition shadow-lg inline-block">
          Get Started Free
        </a>
      </div>
      <p class="mt-4 text-primary-200 text-sm">
        2 free resumes remaining
      </p>
    </div>
  </section>

  <!-- Features -->
  <section class="py-16 bg-white" aria-label="Features">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <div class="grid md:grid-cols-3 gap-8">
        <article class="text-center p-6">
          <div class="w-12 h-12 bg-primary-100 rounded-xl flex items-center justify-center mx-auto mb-4">
            <svg class="w-6 h-6 text-primary-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 10V3L4 14h7v7l9-11h-7z"/>
            </svg>
          </div>
          <h2 class="text-lg font-semibold text-slate-900 mb-2">Quick & Easy Resume Builder</h2>
          <p class="text-slate-600">Step-by-step form guides you through building your resume in under 10 minutes.</p>
        </article>
        <article class="text-center p-6">
          <div class="w-12 h-12 bg-primary-100 rounded-xl flex items-center justify-center mx-auto mb-4">
            <svg class="w-6 h-6 text-primary-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"/>
            </svg>
          </div>
          <h2 class="text-lg font-semibold text-slate-900 mb-2">Professional Resume Templates</h2>
          <p class="text-slate-600">ATS-friendly designs crafted for different industries and career stages.</p>
        </article>
        <article class="text-center p-6">
          <div class="w-12 h-12 bg-primary-100 rounded-xl flex items-center justify-center mx-auto mb-4">
            <svg class="w-6 h-6 text-primary-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 16v1a3 3 0 003 3h10a3 3 0 003-3v-1m-4-4l-4 4m0 0l-4-4m4 4V4"/>
            </svg>
          </div>
          <h2 class="text-lg font-semibold text-slate-900 mb-2">PDF Resume Export</h2>
          <p class="text-slate-600">Download high-quality PDFs ready for job applications and email submissions.</p>
        </article>
      </div>
    </div>
  </section>

  <!-- Templates Section -->
  <section id="templates" class="py-16 bg-slate-50" aria-label="Resume Templates">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <header class="text-center mb-12">
        <h2 class="text-3xl font-bold text-slate-900 mb-4">Choose Your Resume Template</h2>
        <p class="text-slate-600 max-w-xl mx-auto">Select a design that matches your industry and personal style. All templates are fully customizable.</p>
      </header>

      <div class="grid md:grid-cols-3 gap-8">
        <!-- Modern Template -->
        <article class="bg-white rounded-2xl shadow-lg overflow-hidden cursor-pointer transform transition hover:scale-105 hover:shadow-xl border-2 border-transparent hover:border-primary-500">
          <figure class="h-64 bg-gradient-to-br from-blue-500 to-indigo-600 p-6 flex flex-col justify-center items-center text-white">
            <figcaption class="text-center">
              <h3 class="text-2xl font-bold mb-1">John Doe</h3>
              <p class="text-blue-100 text-sm">Software Engineer</p>
              <div class="mt-4 w-full h-px bg-blue-300"></div>
              <div class="mt-2 text-xs text-blue-100 space-y-1">
                <p>john@email.com | (555) 123-4567</p>
                <p>San Francisco, CA</p>
              </div>
            </figcaption>
          </figure>
          <div class="p-6">
            <h3 class="text-lg font-bold text-slate-900">Modern Resume Template</h3>
            <p class="text-slate-600 text-sm mt-1">Clean, bold header with vibrant colors. Perfect for tech professionals and creatives.</p>
            <div class="mt-4 flex gap-2">
              <span class="px-2 py-1 bg-blue-100 text-blue-700 text-xs rounded-full font-medium">Tech</span>
              <span class="px-2 py-1 bg-purple-100 text-purple-700 text-xs rounded-full font-medium">Creative</span>
            </div>
          </div>
        </article>

        <!-- Classic Template -->
        <article class="bg-white rounded-2xl shadow-lg overflow-hidden cursor-pointer transform transition hover:scale-105 hover:shadow-xl border-2 border-transparent hover:border-primary-500">
          <figure class="h-64 bg-white p-6 flex flex-col justify-center items-center text-slate-800 border border-slate-200">
            <figcaption class="text-center">
              <h3 class="text-2xl font-bold mb-1 font-serif">Jane Smith</h3>
              <p class="text-amber-700 text-sm italic font-serif">Attorney at Law</p>
              <div class="mt-4 w-full h-px bg-amber-700"></div>
              <div class="mt-2 text-xs text-slate-600 space-y-1 font-serif">
                <p>jane@lawfirm.com | (555) 987-6543</p>
                <p>New York, NY</p>
              </div>
            </figcaption>
          </figure>
          <div class="p-6">
            <h3 class="text-lg font-bold text-slate-900">Classic Resume Template</h3>
            <p class="text-slate-600 text-sm mt-1">Traditional serif typography with elegant layout. Ideal for law, medicine, and academia.</p>
            <div class="mt-4 flex gap-2">
              <span class="px-2 py-1 bg-amber-100 text-amber-700 text-xs rounded-full font-medium">Law</span>
              <span class="px-2 py-1 bg-green-100 text-green-700 text-xs rounded-full font-medium">Medicine</span>
            </div>
          </div>
        </article>

        <!-- Professional Template -->
        <article class="bg-white rounded-2xl shadow-lg overflow-hidden cursor-pointer transform transition hover:scale-105 hover:shadow-xl border-2 border-transparent hover:border-primary-500">
          <figure class="h-64 bg-slate-50 p-0 flex">
            <div class="w-1/3 bg-teal-700 p-4 flex flex-col justify-center text-white">
              <h3 class="text-sm font-bold">A. Johnson</h3>
              <p class="text-teal-200 text-xs">Business Analyst</p>
            </div>
            <div class="w-2/3 p-4 flex flex-col justify-center">
              <div class="text-xs text-slate-600 space-y-1">
                <p>alex@company.com</p>
                <p>(555) 456-7890</p>
              </div>
            </div>
          </figure>
          <div class="p-6">
            <h3 class="text-lg font-bold text-slate-900">Professional Resume Template</h3>
            <p class="text-slate-600 text-sm mt-1">Two-column layout with sidebar. Great for business, management, and consulting roles.</p>
            <div class="mt-4 flex gap-2">
              <span class="px-2 py-1 bg-teal-100 text-teal-700 text-xs rounded-full font-medium">Business</span>
              <span class="px-2 py-1 bg-slate-100 text-slate-700 text-xs rounded-full font-medium">Management</span>
            </div>
          </div>
        </article>
      </div>
    </div>
  </section>

  <!-- Pricing CTA Section -->
  <section class="py-16 bg-gradient-to-br from-primary-600 to-primary-800 text-white" aria-label="Pricing">
    <div class="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 text-center">
      <h2 class="text-3xl font-bold mb-4">Need More Than 2 Resumes?</h2>
      <p class="text-primary-100 text-lg mb-8 max-w-xl mx-auto">
        Upgrade to Unlimited for a one-time payment of $9.99. Create as many resumes as you need, edit anytime, and never worry about limits again.
      </p>
      <div class="flex flex-col sm:flex-row justify-center gap-4">
        <div class="bg-white/10 backdrop-blur rounded-xl p-6 text-left flex-1 max-w-xs">
          <h3 class="font-bold text-lg mb-2">Free</h3>
          <p class="text-primary-200 text-sm mb-4">2 resume generations</p>
          <p class="text-3xl font-bold">$0</p>
        </div>
        <div class="bg-white rounded-xl p-6 text-left flex-1 max-w-xs text-slate-900 relative">
          <div class="absolute -top-3 left-1/2 -translate-x-1/2 px-3 py-0.5 bg-amber-400 text-amber-900 text-xs font-bold rounded-full">BEST VALUE</div>
          <h3 class="font-bold text-lg mb-2">Unlimited</h3>
          <p class="text-slate-500 text-sm mb-4">Unlimited resumes forever</p>
          <p class="text-3xl font-bold text-primary-600">$9.99</p>
          <p class="text-slate-400 text-xs">One-time payment</p>
        </div>
      </div>
      <a href="/pricing" class="mt-8 px-8 py-3 bg-white text-primary-700 rounded-xl font-semibold hover:bg-primary-50 transition shadow-lg inline-block">
        Upgrade to Unlimited
      </a>
    </div>
  </section>

  <!-- Target Audience -->
  <section class="py-16 bg-white" aria-label="Who ResumeForge is For">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <header class="text-center mb-12">
        <h2 class="text-3xl font-bold text-slate-900 mb-4">Built for Every Professional</h2>
        <p class="text-slate-600">Whether you're just starting out or a seasoned expert, we have the right template for you.</p>
      </header>
      <nav class="flex flex-wrap justify-center gap-4" aria-label="Target professions">
        <div class="px-6 py-3 bg-slate-100 rounded-full text-slate-700 font-medium flex items-center gap-2">
          <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 14l9-5-9-5-9 5 9 5z"/></svg>
          Students & Graduates
        </div>
        <div class="px-6 py-3 bg-slate-100 rounded-full text-slate-700 font-medium flex items-center gap-2">
          <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 6l3 1m0 0l-3 9a5.002 5.002 0 006.001 0M6 7l3 9M6 7l6-2m6 2l3-1m-3 1l-3 9a5.002 5.002 0 006.001 0M18 7l3 9m-3-9l-6-2m0-2v2m0 16V5m0 16H9m3 0h3"/></svg>
          Lawyers
        </div>
        <div class="px-6 py-3 bg-slate-100 rounded-full text-slate-700 font-medium flex items-center gap-2">
          <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z"/></svg>
          Doctors & Healthcare
        </div>
        <div class="px-6 py-3 bg-slate-100 rounded-full text-slate-700 font-medium flex items-center gap-2">
          <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 20l4-16m4 4l4 4-4 4M6 16l-4-4 4-4"/></svg>
          Programmers
        </div>
        <div class="px-6 py-3 bg-slate-100 rounded-full text-slate-700 font-medium flex items-center gap-2">
          <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 13.255A23.931 23.931 0 0112 15c-3.183 0-6.22-.62-9-1.745M16 6V4a2 2 0 00-2-2h-4a2 2 0 00-2 2v2m4 6h.01M5 20h14a2 2 0 002-2V8a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z"/></svg>
          Businessmen & Entrepreneurs
        </div>
      </nav>
    </div>
  </section>
</div>
`

function prerender() {
  console.log('🚀 Starting prerender...')

  if (!fs.existsSync(indexPath)) {
    console.error('❌ dist/index.html not found. Run "npm run build" first.')
    process.exit(1)
  }

  let html = fs.readFileSync(indexPath, 'utf-8')

  // Inject the static snapshot into the app div
  // This ensures search engines see all content immediately
  html = html.replace(
    '<div id="app"></div>',
    `<div id="app">${homePageSnapshot}</div>`
  )

  // Add a data attribute to indicate prerendered content
  // Vue will hydrate over this during client-side initialization
  html = html.replace(
    '<div id="app">',
    '<div id="app" data-server-rendered="true">'
  )

  fs.writeFileSync(indexPath, html)

  // Also create a 200.html for SPA fallback (some hosts use this)
  fs.writeFileSync(path.join(distPath, '200.html'), html)

  console.log('✅ Prerender complete!')
  console.log(`📄 ${indexPath} — homepage content injected for SEO`)
  console.log(`📄 ${path.join(distPath, '200.html')} — SPA fallback created`)
}

prerender()

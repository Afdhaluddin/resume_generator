<template>
  <main>
    <!-- Hero Section -->
    <section class="bg-gradient-to-br from-primary-600 to-primary-800 text-white py-20" aria-label="Hero">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 text-center">
        <h1 class="text-4xl sm:text-5xl lg:text-6xl font-bold mb-6">
          Build Your Professional Resume Online — Free
        </h1>
        <p class="text-xl text-primary-100 mb-8 max-w-2xl mx-auto">
          Create stunning, ATS-friendly resumes in minutes. Choose from 7 professionally designed templates tailored for students, doctors, lawyers, programmers, and business professionals.
        </p>
        <div class="flex justify-center gap-4 flex-wrap">
          <button 
            @click="scrollToTemplates"
            class="bg-white text-primary-700 px-8 py-3 rounded-xl font-semibold hover:bg-primary-50 transition shadow-lg"
            aria-label="Start building your resume"
          >
            Get Started Free
          </button>
          <button
            @click="$emit('go-pricing')"
            class="border-2 border-white text-white px-8 py-3 rounded-xl font-semibold hover:bg-white/10 transition"
          >
            See Pricing
          </button>
        </div>
        <p v-if="usageInfo && usageInfo.limit < 1000" class="mt-4 text-primary-200 text-sm" aria-live="polite">
          {{ usageInfo.remaining }} free resume{{ usageInfo.remaining !== 1 ? 's' : '' }} remaining
        </p>
      </div>
    </section>

    <!-- Features -->
    <section class="py-16 bg-white" aria-label="Features">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="grid md:grid-cols-3 gap-8">
          <article class="text-center p-6">
            <div class="w-12 h-12 bg-primary-100 rounded-xl flex items-center justify-center mx-auto mb-4" aria-hidden="true">
              <svg class="w-6 h-6 text-primary-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 10V3L4 14h7v7l9-11h-7z"/>
              </svg>
            </div>
            <h2 class="text-lg font-semibold text-slate-900 mb-2">Quick & Easy Resume Builder</h2>
            <p class="text-slate-600">Step-by-step form guides you through building your resume in under 10 minutes.</p>
          </article>
          <article class="text-center p-6">
            <div class="w-12 h-12 bg-primary-100 rounded-xl flex items-center justify-center mx-auto mb-4" aria-hidden="true">
              <svg class="w-6 h-6 text-primary-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"/>
              </svg>
            </div>
            <h2 class="text-lg font-semibold text-slate-900 mb-2">6 Professional Templates</h2>
            <p class="text-slate-600">Modern, Classic, Professional, Minimal, Executive, and Creative designs.</p>
          </article>
          <article class="text-center p-6">
            <div class="w-12 h-12 bg-primary-100 rounded-xl flex items-center justify-center mx-auto mb-4" aria-hidden="true">
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
          <p class="text-slate-600 max-w-xl mx-auto">7 professionally designed templates for every industry. All ATS-friendly and fully customizable.</p>
        </header>

        <div class="grid md:grid-cols-2 lg:grid-cols-3 gap-8" role="list">
          <!-- Modern Template -->
          <article 
            class="bg-white rounded-2xl shadow-lg overflow-hidden cursor-pointer transform transition hover:scale-105 hover:shadow-xl border-2 border-transparent hover:border-primary-500"
            @click="$emit('select-template', 'modern')"
            role="listitem"
          >
            <figure class="h-56 bg-gradient-to-br from-blue-500 to-indigo-600 p-6 flex flex-col justify-center items-center text-white">
              <figcaption class="text-center">
                <h3 class="text-2xl font-bold mb-1">John Doe</h3>
                <p class="text-blue-100 text-sm">Software Engineer</p>
                <div class="mt-4 w-full h-px bg-blue-300"></div>
                <div class="mt-2 text-xs text-blue-100 space-y-1">
                  <p>john@email.com · (555) 123-4567</p>
                  <p>San Francisco, CA</p>
                </div>
              </figcaption>
            </figure>
            <div class="p-6">
              <h3 class="text-lg font-bold text-slate-900">Modern</h3>
              <p class="text-slate-600 text-sm mt-1">Bold blue header. Perfect for tech, startups, and creatives.</p>
              <div class="mt-4 flex gap-2 flex-wrap">
                <span class="px-2 py-1 bg-blue-100 text-blue-700 text-xs rounded-full font-medium">Tech</span>
                <span class="px-2 py-1 bg-indigo-100 text-indigo-700 text-xs rounded-full font-medium">Startups</span>
              </div>
            </div>
          </article>

          <!-- Classic Template -->
          <article 
            class="bg-white rounded-2xl shadow-lg overflow-hidden cursor-pointer transform transition hover:scale-105 hover:shadow-xl border-2 border-transparent hover:border-primary-500"
            @click="$emit('select-template', 'classic')"
            role="listitem"
          >
            <figure class="h-56 bg-white p-6 flex flex-col justify-center items-center text-slate-800 border border-slate-200">
              <figcaption class="text-center">
                <h3 class="text-2xl font-bold mb-1 font-serif">Jane Smith</h3>
                <p class="text-amber-700 text-sm italic font-serif">Attorney at Law</p>
                <div class="mt-4 w-full h-px bg-amber-700"></div>
                <div class="mt-2 text-xs text-slate-600 space-y-1 font-serif">
                  <p>jane@lawfirm.com · (555) 987-6543</p>
                  <p>New York, NY</p>
                </div>
              </figcaption>
            </figure>
            <div class="p-6">
              <h3 class="text-lg font-bold text-slate-900">Classic</h3>
              <p class="text-slate-600 text-sm mt-1">Elegant serif typography. Ideal for law, medicine, and academia.</p>
              <div class="mt-4 flex gap-2 flex-wrap">
                <span class="px-2 py-1 bg-amber-100 text-amber-700 text-xs rounded-full font-medium">Law</span>
                <span class="px-2 py-1 bg-green-100 text-green-700 text-xs rounded-full font-medium">Medicine</span>
              </div>
            </div>
          </article>

          <!-- Professional Template -->
          <article 
            class="bg-white rounded-2xl shadow-lg overflow-hidden cursor-pointer transform transition hover:scale-105 hover:shadow-xl border-2 border-transparent hover:border-primary-500"
            @click="$emit('select-template', 'professional')"
            role="listitem"
          >
            <figure class="h-56 bg-slate-50 p-0 flex">
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
              <h3 class="text-lg font-bold text-slate-900">Professional</h3>
              <p class="text-slate-600 text-sm mt-1">Two-column with sidebar. Great for business and management.</p>
              <div class="mt-4 flex gap-2 flex-wrap">
                <span class="px-2 py-1 bg-teal-100 text-teal-700 text-xs rounded-full font-medium">Business</span>
                <span class="px-2 py-1 bg-slate-100 text-slate-700 text-xs rounded-full font-medium">Management</span>
              </div>
            </div>
          </article>

          <!-- Minimal Template -->
          <article 
            class="bg-white rounded-2xl shadow-lg overflow-hidden cursor-pointer transform transition hover:scale-105 hover:shadow-xl border-2 border-transparent hover:border-primary-500"
            @click="$emit('select-template', 'minimal')"
            role="listitem"
          >
            <figure class="h-56 bg-white p-8 flex flex-col justify-center text-slate-800 border border-slate-100">
              <figcaption>
                <h3 class="text-3xl font-light mb-1 tracking-tight">Sarah Chen</h3>
                <p class="text-gray-400 text-sm font-light">UX Designer</p>
                <div class="mt-6 w-12 h-px bg-gray-200"></div>
                <div class="mt-3 text-xs text-gray-400 space-y-1">
                  <p>sarah@design.co · Portland, OR</p>
                </div>
              </figcaption>
            </figure>
            <div class="p-6">
              <h3 class="text-lg font-bold text-slate-900">Minimal</h3>
              <p class="text-slate-600 text-sm mt-1">Ultra-clean with whitespace. For designers and marketers.</p>
              <div class="mt-4 flex gap-2 flex-wrap">
                <span class="px-2 py-1 bg-gray-100 text-gray-700 text-xs rounded-full font-medium">Design</span>
                <span class="px-2 py-1 bg-rose-100 text-rose-700 text-xs rounded-full font-medium">Marketing</span>
              </div>
            </div>
          </article>

          <!-- Executive Template -->
          <article 
            class="bg-white rounded-2xl shadow-lg overflow-hidden cursor-pointer transform transition hover:scale-105 hover:shadow-xl border-2 border-transparent hover:border-primary-500"
            @click="$emit('select-template', 'executive')"
            role="listitem"
          >
            <figure class="h-56 bg-slate-900 p-6 flex flex-col justify-center text-white">
              <figcaption>
                <h3 class="text-xl font-bold mb-1">Robert Williams</h3>
                <p class="text-amber-400 text-xs tracking-widest uppercase">Chief Executive Officer</p>
                <div class="mt-4 w-16 h-px bg-amber-400"></div>
                <div class="mt-3 text-xs text-slate-400 space-y-1">
                  <p>robert@corp.com · New York, NY</p>
                </div>
              </figcaption>
            </figure>
            <div class="p-6">
              <h3 class="text-lg font-bold text-slate-900">Executive</h3>
              <p class="text-slate-600 text-sm mt-1">Bold dark header with gold accents. For C-suite and leaders.</p>
              <div class="mt-4 flex gap-2 flex-wrap">
                <span class="px-2 py-1 bg-slate-800 text-slate-200 text-xs rounded-full font-medium">C-Suite</span>
                <span class="px-2 py-1 bg-yellow-100 text-yellow-800 text-xs rounded-full font-medium">Leadership</span>
              </div>
            </div>
          </article>

          <!-- Creative Template -->
          <article 
            class="bg-white rounded-2xl shadow-lg overflow-hidden cursor-pointer transform transition hover:scale-105 hover:shadow-xl border-2 border-transparent hover:border-primary-500"
            @click="$emit('select-template', 'creative')"
            role="listitem"
          >
            <figure class="h-56 bg-white p-0 flex">
              <div class="w-2 bg-pink-600"></div>
              <div class="flex-1 p-6 flex flex-col justify-center">
                <figcaption>
                  <h3 class="text-2xl font-bold text-slate-800 mb-1">Mia Torres</h3>
                  <p class="text-pink-600 text-sm font-semibold">Art Director</p>
                  <div class="mt-4 w-full h-px bg-pink-100"></div>
                  <div class="mt-2 text-xs text-slate-500 space-y-1">
                    <p>mia@studio.com · Los Angeles, CA</p>
                  </div>
                </figcaption>
              </div>
            </figure>
            <div class="p-6">
              <h3 class="text-lg font-bold text-slate-900">Creative</h3>
              <p class="text-slate-600 text-sm mt-1">Vibrant accent bar with modern layout. For artists and creators.</p>
              <div class="mt-4 flex gap-2 flex-wrap">
                <span class="px-2 py-1 bg-pink-100 text-pink-700 text-xs rounded-full font-medium">Artists</span>
                <span class="px-2 py-1 bg-fuchsia-100 text-fuchsia-700 text-xs rounded-full font-medium">Content</span>
              </div>
            </div>
          </article>

          <!-- Academic Template -->
          <article 
            class="bg-white rounded-2xl shadow-lg overflow-hidden cursor-pointer transform transition hover:scale-105 hover:shadow-xl border-2 border-transparent hover:border-primary-500"
            @click="$emit('select-template', 'academic')"
            role="listitem"
          >
            <figure class="h-56 bg-white p-0 flex">
              <div class="w-1/2 p-4 flex flex-col justify-center border-r border-slate-100">
                <figcaption>
                  <h3 class="text-lg font-bold text-slate-800 mb-1">Dr. A. Smith</h3>
                  <p class="text-red-800 text-xs font-semibold">Research Scientist</p>
                  <div class="mt-3 w-full h-px bg-amber-200"></div>
                  <div class="mt-2 text-xs text-slate-500 space-y-1">
                    <p>alex@university.edu</p>
                  </div>
                </figcaption>
              </div>
              <div class="w-1/2 p-4 flex flex-col justify-center">
                <div class="text-xs text-slate-600 space-y-1">
                  <p class="font-semibold text-red-800">PUBLICATIONS</p>
                  <p>3 Journal Articles</p>
                  <p>2 Books</p>
                  <p class="font-semibold text-red-800 mt-2">REFEREES</p>
                  <p>Prof. J. Doe</p>
                </div>
              </div>
            </figure>
            <div class="p-6">
              <h3 class="text-lg font-bold text-slate-900">Academic</h3>
              <p class="text-slate-600 text-sm mt-1">Research CV with publications, projects, and referees. For PhDs and researchers.</p>
              <div class="mt-4 flex gap-2 flex-wrap">
                <span class="px-2 py-1 bg-red-100 text-red-800 text-xs rounded-full font-medium">Research</span>
                <span class="px-2 py-1 bg-amber-100 text-amber-800 text-xs rounded-full font-medium">PhD</span>
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
        <button 
          @click="$emit('go-pricing')"
          class="mt-8 px-8 py-3 bg-white text-primary-700 rounded-xl font-semibold hover:bg-primary-50 transition shadow-lg"
        >
          Upgrade to Unlimited
        </button>
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
          <div class="px-6 py-3 bg-slate-100 rounded-full text-slate-700 font-medium flex items-center gap-2" role="listitem">
            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24" aria-hidden="true"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 14l9-5-9-5-9 5 9 5z"/></svg>
            Students & Graduates
          </div>
          <div class="px-6 py-3 bg-slate-100 rounded-full text-slate-700 font-medium flex items-center gap-2" role="listitem">
            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24" aria-hidden="true"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 6l3 1m0 0l-3 9a5.002 5.002 0 006.001 0M6 7l3 9M6 7l6-2m6 2l3-1m-3 1l-3 9a5.002 5.002 0 006.001 0M18 7l3 9m-3-9l-6-2m0-2v2m0 16V5m0 16H9m3 0h3"/></svg>
            Lawyers
          </div>
          <div class="px-6 py-3 bg-slate-100 rounded-full text-slate-700 font-medium flex items-center gap-2" role="listitem">
            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24" aria-hidden="true"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z"/></svg>
            Doctors & Healthcare
          </div>
          <div class="px-6 py-3 bg-slate-100 rounded-full text-slate-700 font-medium flex items-center gap-2" role="listitem">
            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24" aria-hidden="true"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 20l4-16m4 4l4 4-4 4M6 16l-4-4 4-4"/></svg>
            Programmers
          </div>
          <div class="px-6 py-3 bg-slate-100 rounded-full text-slate-700 font-medium flex items-center gap-2" role="listitem">
            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24" aria-hidden="true"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 13.255A23.931 23.931 0 0112 15c-3.183 0-6.22-.62-9-1.745M16 6V4a2 2 0 00-2-2h-4a2 2 0 00-2 2v2m4 6h.01M5 20h14a2 2 0 002-2V8a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z"/></svg>
            Businessmen & Entrepreneurs
          </div>
        </nav>
      </div>
    </section>

    <!-- JSON-LD FAQ Structured Data -->
    <script type="application/ld+json">
    {
      "@context": "https://schema.org",
      "@type": "FAQPage",
      "mainEntity": [
        {
          "@type": "Question",
          "name": "Is ResumeForge free?",
          "acceptedAnswer": {
            "@type": "Answer",
            "text": "Yes! ResumeForge is free for your first 2 resume generations. After that, you can upgrade for unlimited access."
          }
        },
        {
          "@type": "Question",
          "name": "What resume templates are available?",
          "acceptedAnswer": {
            "@type": "Answer",
            "text": "We offer 6 professional templates: Modern, Classic, Professional, Minimal, Executive, and Creative. Each is designed for different industries and career stages."
          }
        },
        {
          "@type": "Question",
          "name": "Can I edit my resume after generating it?",
          "acceptedAnswer": {
            "@type": "Answer",
            "text": "Yes! Your resume is cached with a unique ID so you can come back and edit it anytime before downloading."
          }
        },
        {
          "@type": "Question",
          "name": "Are the resumes ATS-friendly?",
          "acceptedAnswer": {
            "@type": "Answer",
            "text": "Yes, all our templates are designed to be ATS (Applicant Tracking System) friendly, ensuring your resume gets past automated screening."
          }
        }
      ]
    }
    </script>
  </main>
</template>

<script setup>
import { onMounted } from 'vue'
import { useSeo } from '@/composables/useSeo.js'

defineProps({
  usageInfo: Object
})

defineEmits(['select-template', 'go-pricing'])

onMounted(() => {
  useSeo({
    title: 'Free Resume Builder — 6 Professional Templates',
    description: 'Build professional, ATS-friendly resumes in minutes with ResumeForge. 6 templates: Modern, Classic, Professional, Minimal, Executive, Creative. Free for first 2 resumes.',
    url: 'https://resumeforge.app/'
  })
})

const scrollToTemplates = () => {
  document.getElementById('templates').scrollIntoView({ behavior: 'smooth' })
}
</script>

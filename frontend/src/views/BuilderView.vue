<template>
  <main class="max-w-4xl mx-auto px-4 py-8">
    <div class="flex justify-between items-center mb-4">
      <StepIndicator :steps="steps" :current-step="currentStep" @go-to-step="goToStep" />
      <button
        @click="fillDemoData"
        class="px-4 py-2 bg-amber-100 text-amber-800 rounded-lg hover:bg-amber-200 transition text-sm font-medium flex items-center gap-2 shrink-0 ml-4"
        title="Fill all fields with realistic demo data"
      >
        <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 10V3L4 14h7v7l9-11h-7z"/></svg>
        Autofill Demo Data
      </button>
    </div>

    <!-- Payment Required Modal -->
    <Transition name="fade">
      <div v-if="showPaymentModal" class="fixed inset-0 bg-black/50 z-50 flex items-center justify-center p-4" @click.self="showPaymentModal = false">
        <div class="bg-white rounded-2xl shadow-2xl p-8 max-w-md w-full text-center">
          <div class="w-16 h-16 bg-amber-100 rounded-full flex items-center justify-center mx-auto mb-4">
            <svg class="w-8 h-8 text-amber-600" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 10V3L4 14h7v7l9-11h-7z"/></svg>
          </div>
          <h2 class="text-2xl font-bold text-slate-900 mb-2">You've Used Your Free Resumes</h2>
          <p class="text-slate-600 mb-6">Upgrade to Unlimited for just $9.99 (one-time) and create as many resumes as you need.</p>
          <div class="flex flex-col gap-3">
            <button 
              @click="goToPricing"
              class="w-full py-3 bg-primary-600 text-white rounded-xl font-semibold hover:bg-primary-700 transition"
            >
              Upgrade to Unlimited — $9.99
            </button>
            <button 
              @click="showPaymentModal = false"
              class="w-full py-3 border border-slate-300 text-slate-700 rounded-xl font-medium hover:bg-slate-50 transition"
            >
              Maybe Later
            </button>
          </div>
        </div>
      </div>
    </Transition>

    <!-- Form Content -->
    <div class="bg-white rounded-2xl shadow-lg p-6 sm:p-8">
      <Transition name="step" mode="out-in">
        <!-- Step 1: Personal Info -->
        <section v-if="currentStep === 0" key="personal" aria-label="Personal Information">
          <h2 class="text-2xl font-bold text-slate-900 mb-6">Personal Information</h2>
          <div class="grid sm:grid-cols-2 gap-4">
            <div class="sm:col-span-2">
              <label class="block text-sm font-medium text-slate-700 mb-1" for="fullName">Full Name *</label>
              <input id="fullName" v-model="form.personalInfo.fullName" type="text" class="w-full px-4 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-primary-500 outline-none" placeholder="e.g. John Doe" required />
            </div>
            <div class="sm:col-span-2">
              <label class="block text-sm font-medium text-slate-700 mb-1" for="jobTitle">Job Title *</label>
              <input id="jobTitle" v-model="form.personalInfo.jobTitle" type="text" class="w-full px-4 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-primary-500 outline-none" placeholder="e.g. Software Engineer" required />
            </div>
            <div>
              <label class="block text-sm font-medium text-slate-700 mb-1" for="email">Email *</label>
              <input id="email" v-model="form.personalInfo.email" type="email" class="w-full px-4 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-primary-500 outline-none" placeholder="john@example.com" required />
            </div>
            <div>
              <label class="block text-sm font-medium text-slate-700 mb-1" for="phone">Phone</label>
              <input id="phone" v-model="form.personalInfo.phone" type="tel" class="w-full px-4 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-primary-500 outline-none" placeholder="(555) 123-4567" />
            </div>
            <div>
              <label class="block text-sm font-medium text-slate-700 mb-1" for="city">City</label>
              <input id="city" v-model="form.personalInfo.city" type="text" class="w-full px-4 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-primary-500 outline-none" placeholder="San Francisco" />
            </div>
            <div>
              <label class="block text-sm font-medium text-slate-700 mb-1" for="country">Country</label>
              <input id="country" v-model="form.personalInfo.country" type="text" class="w-full px-4 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-primary-500 outline-none" placeholder="USA" />
            </div>
            <div class="sm:col-span-2">
              <label class="block text-sm font-medium text-slate-700 mb-1" for="address">Address</label>
              <input id="address" v-model="form.personalInfo.address" type="text" class="w-full px-4 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-primary-500 outline-none" placeholder="123 Main St" />
            </div>
            <div>
              <label class="block text-sm font-medium text-slate-700 mb-1" for="linkedin">LinkedIn</label>
              <input id="linkedin" v-model="form.personalInfo.linkedIn" type="text" class="w-full px-4 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-primary-500 outline-none" placeholder="linkedin.com/in/johndoe" />
            </div>
            <div>
              <label class="block text-sm font-medium text-slate-700 mb-1" for="website">Website</label>
              <input id="website" v-model="form.personalInfo.website" type="text" class="w-full px-4 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-primary-500 outline-none" placeholder="johndoe.com" />
            </div>
            <div>
              <label class="block text-sm font-medium text-slate-700 mb-1" for="twitter">X/Twitter</label>
              <input id="twitter" v-model="form.personalInfo.twitter" type="text" class="w-full px-4 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-primary-500 outline-none" placeholder="@handle" />
            </div>
            <div>
              <label class="block text-sm font-medium text-slate-700 mb-1" for="github">GitHub</label>
              <input id="github" v-model="form.personalInfo.github" type="text" class="w-full px-4 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-primary-500 outline-none" placeholder="username" />
            </div>
            <div>
              <label class="block text-sm font-medium text-slate-700 mb-1" for="orcid">ORCID</label>
              <input id="orcid" v-model="form.personalInfo.orcid" type="text" class="w-full px-4 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-primary-500 outline-none" placeholder="0000-0000-0000-0000" />
            </div>
            <div>
              <label class="block text-sm font-medium text-slate-700 mb-1" for="gitlab">GitLab</label>
              <input id="gitlab" v-model="form.personalInfo.gitlab" type="text" class="w-full px-4 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-primary-500 outline-none" placeholder="username" />
            </div>
            <div>
              <label class="block text-sm font-medium text-slate-700 mb-1" for="mastodon">Mastodon</label>
              <input id="mastodon" v-model="form.personalInfo.mastodon" type="text" class="w-full px-4 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-primary-500 outline-none" placeholder="@user@instance" />
            </div>
          </div>
        </section>

        <!-- Step 2: Summary -->
        <section v-else-if="currentStep === 1" key="summary" aria-label="Professional Summary">
          <h2 class="text-2xl font-bold text-slate-900 mb-6">Professional Summary</h2>
          <div>
            <label class="block text-sm font-medium text-slate-700 mb-1" for="summary">Summary</label>
            <textarea 
              id="summary"
              v-model="form.summary" 
              rows="6" 
              class="w-full px-4 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-primary-500 outline-none resize-none"
              placeholder="Write a brief summary of your professional background, key skills, and career goals..."
            ></textarea>
            <p class="text-xs text-slate-500 mt-1">Tip: Keep it concise (2-4 sentences). Highlight your experience and what makes you unique.</p>
          </div>
          <figure class="mt-6 p-4 bg-blue-50 rounded-lg">
            <figcaption class="font-semibold text-blue-900 text-sm mb-2">Example for a Programmer:</figcaption>
            <p class="text-sm text-blue-800">"Full-stack developer with 5+ years of experience building scalable web applications using React, Node.js, and cloud technologies. Passionate about clean code, user experience, and mentoring junior developers. Proven track record of delivering high-impact projects for Fortune 500 clients."</p>
          </figure>
        </section>

        <!-- Step 3: Experience -->
        <section v-else-if="currentStep === 2" key="experience" aria-label="Work Experience">
          <div class="flex justify-between items-center mb-6">
            <h2 class="text-2xl font-bold text-slate-900">Work Experience</h2>
            <button 
              @click="addExperience"
              class="px-4 py-2 bg-primary-600 text-white rounded-lg hover:bg-primary-700 transition text-sm font-medium flex items-center gap-2"
              aria-label="Add work experience entry"
            >
              <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4"/></svg>
              Add Experience
            </button>
          </div>
          
          <div v-if="form.experience.length === 0" class="text-center py-12 bg-slate-50 rounded-xl">
            <p class="text-slate-500">No experience added yet. Click "Add Experience" to get started.</p>
          </div>

          <div v-for="(exp, index) in form.experience" :key="index" class="border border-slate-200 rounded-xl p-4 mb-4 relative">
            <button 
              @click="removeExperience(index)"
              class="absolute top-3 right-3 text-red-500 hover:text-red-700"
              :aria-label="`Remove experience entry ${index + 1}`"
            >
              <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"/></svg>
            </button>
            <fieldset class="grid sm:grid-cols-2 gap-4">
              <legend class="sr-only">Work Experience Entry {{ index + 1 }}</legend>
              <div>
                <label class="block text-sm font-medium text-slate-700 mb-1" :for="`company-${index}`">Company *</label>
                <input :id="`company-${index}`" v-model="exp.company" type="text" class="w-full px-3 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-primary-500 outline-none" placeholder="Company Name" />
              </div>
              <div>
                <label class="block text-sm font-medium text-slate-700 mb-1" :for="`position-${index}`">Position *</label>
                <input :id="`position-${index}`" v-model="exp.position" type="text" class="w-full px-3 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-primary-500 outline-none" placeholder="Job Title" />
              </div>
              <div>
                <label class="block text-sm font-medium text-slate-700 mb-1" :for="`startDate-${index}`">Start Date</label>
                <input :id="`startDate-${index}`" v-model="exp.startDate" type="text" class="w-full px-3 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-primary-500 outline-none" placeholder="Jan 2020" />
              </div>
              <div>
                <label class="block text-sm font-medium text-slate-700 mb-1" :for="`endDate-${index}`">End Date</label>
                <input :id="`endDate-${index}`" v-model="exp.endDate" type="text" class="w-full px-3 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-primary-500 outline-none" placeholder="Dec 2023 (or Present)" />
              </div>
              <div class="sm:col-span-2">
                <label class="block text-sm font-medium text-slate-700 mb-1" :for="`desc-${index}`">Description</label>
                <textarea :id="`desc-${index}`" v-model="exp.description" rows="2" class="w-full px-3 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-primary-500 outline-none resize-none" placeholder="Brief description of your role..."></textarea>
              </div>
              <div class="sm:col-span-2">
                <label class="block text-sm font-medium text-slate-700 mb-1" :for="`achievements-${index}`">Key Achievements (one per line)</label>
                <textarea 
                  :id="`achievements-${index}`"
                  v-model="exp.achievementsText" 
                  @input="updateAchievements(exp)"
                  rows="3" 
                  class="w-full px-3 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-primary-500 outline-none resize-none" 
                  placeholder="- Increased sales by 25%&#10;- Led a team of 5 developers&#10;- Reduced load time by 40%"
                ></textarea>
              </div>
            </fieldset>
          </div>
        </section>

        <!-- Step 4: Education -->
        <section v-else-if="currentStep === 3" key="education" aria-label="Education">
          <div class="flex justify-between items-center mb-6">
            <h2 class="text-2xl font-bold text-slate-900">Education</h2>
            <button 
              @click="addEducation"
              class="px-4 py-2 bg-primary-600 text-white rounded-lg hover:bg-primary-700 transition text-sm font-medium flex items-center gap-2"
              aria-label="Add education entry"
            >
              <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4"/></svg>
              Add Education
            </button>
          </div>
          
          <div v-if="form.education.length === 0" class="text-center py-12 bg-slate-50 rounded-xl">
            <p class="text-slate-500">No education added yet. Click "Add Education" to get started.</p>
          </div>

          <div v-for="(edu, index) in form.education" :key="index" class="border border-slate-200 rounded-xl p-4 mb-4 relative">
            <button 
              @click="removeEducation(index)"
              class="absolute top-3 right-3 text-red-500 hover:text-red-700"
              :aria-label="`Remove education entry ${index + 1}`"
            >
              <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"/></svg>
            </button>
            <fieldset class="grid sm:grid-cols-2 gap-4">
              <legend class="sr-only">Education Entry {{ index + 1 }}</legend>
              <div class="sm:col-span-2">
                <label class="block text-sm font-medium text-slate-700 mb-1" :for="`institution-${index}`">Institution *</label>
                <input :id="`institution-${index}`" v-model="edu.institution" type="text" class="w-full px-3 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-primary-500 outline-none" placeholder="University Name" />
              </div>
              <div>
                <label class="block text-sm font-medium text-slate-700 mb-1" :for="`degree-${index}`">Degree *</label>
                <input :id="`degree-${index}`" v-model="edu.degree" type="text" class="w-full px-3 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-primary-500 outline-none" placeholder="Bachelor of Science" />
              </div>
              <div>
                <label class="block text-sm font-medium text-slate-700 mb-1" :for="`field-${index}`">Field of Study</label>
                <input :id="`field-${index}`" v-model="edu.fieldOfStudy" type="text" class="w-full px-3 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-primary-500 outline-none" placeholder="Computer Science" />
              </div>
              <div>
                <label class="block text-sm font-medium text-slate-700 mb-1" :for="`eduStart-${index}`">Start Date</label>
                <input :id="`eduStart-${index}`" v-model="edu.startDate" type="text" class="w-full px-3 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-primary-500 outline-none" placeholder="2016" />
              </div>
              <div>
                <label class="block text-sm font-medium text-slate-700 mb-1" :for="`eduEnd-${index}`">End Date</label>
                <input :id="`eduEnd-${index}`" v-model="edu.endDate" type="text" class="w-full px-3 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-primary-500 outline-none" placeholder="2020" />
              </div>
              <div class="sm:col-span-2">
                <label class="block text-sm font-medium text-slate-700 mb-1" :for="`eduDesc-${index}`">Description (optional)</label>
                <textarea :id="`eduDesc-${index}`" v-model="edu.description" rows="2" class="w-full px-3 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-primary-500 outline-none resize-none" placeholder="Honors, GPA, relevant coursework..."></textarea>
              </div>
            </fieldset>
          </div>
        </section>

        <!-- Step 5: Skills & Languages -->
        <section v-else-if="currentStep === 4" key="skills" aria-label="Skills and Languages">
          <h2 class="text-2xl font-bold text-slate-900 mb-6">Skills & Languages</h2>
          
          <div class="mb-6">
            <label class="block text-sm font-medium text-slate-700 mb-2" for="skills">Skills (comma-separated)</label>
            <textarea 
              id="skills"
              v-model="skillsText" 
              @input="updateSkills"
              rows="3" 
              class="w-full px-4 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-primary-500 outline-none resize-none"
              placeholder="JavaScript, Python, Project Management, Data Analysis, Communication..."
            ></textarea>
            <div class="flex flex-wrap gap-2 mt-2" role="list" aria-label="Selected skills">
              <span 
                v-for="skill in form.skills" 
                :key="skill"
                class="px-3 py-1 bg-primary-100 text-primary-700 rounded-full text-sm font-medium"
                role="listitem"
              >
                {{ skill }}
              </span>
            </div>
          </div>

          <div>
            <label class="block text-sm font-medium text-slate-700 mb-2" for="languages">Languages (comma-separated)</label>
            <textarea 
              id="languages"
              v-model="languagesText" 
              @input="updateLanguages"
              rows="2" 
              class="w-full px-4 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-primary-500 outline-none resize-none"
              placeholder="English (Native), Spanish (Fluent), Mandarin (Conversational)..."
            ></textarea>
            <div class="flex flex-wrap gap-2 mt-2" role="list" aria-label="Selected languages">
              <span 
                v-for="lang in form.languages" 
                :key="lang"
                class="px-3 py-1 bg-green-100 text-green-700 rounded-full text-sm font-medium"
                role="listitem"
              >
                {{ lang }}
              </span>
            </div>
          </div>
        </section>

        <!-- Step 6: Additional Info (Optional) -->
        <section v-else-if="currentStep === 5" key="additional" aria-label="Additional Information">
          <h2 class="text-2xl font-bold text-slate-900 mb-2">Additional Information</h2>
          <p class="text-slate-500 text-sm mb-6">Optional fields especially useful for academic and research CVs.</p>

          <!-- Life Philosophy -->
          <div class="mb-6">
            <label class="block text-sm font-medium text-slate-700 mb-1" for="lifePhilosophy">Life Philosophy / Quote</label>
            <textarea 
              id="lifePhilosophy"
              v-model="form.lifePhilosophy" 
              rows="2" 
              class="w-full px-4 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-primary-500 outline-none resize-none"
              placeholder="Something smart or heartfelt, preferably in one sentence..."
            ></textarea>
          </div>

          <!-- Projects -->
          <div class="mb-6">
            <div class="flex justify-between items-center mb-2">
              <label class="block text-sm font-medium text-slate-700">Projects</label>
              <button @click="addProject" class="px-3 py-1 bg-primary-600 text-white rounded-lg hover:bg-primary-700 transition text-xs font-medium">Add Project</button>
            </div>
            <div v-for="(proj, index) in form.projects" :key="index" class="border border-slate-200 rounded-xl p-4 mb-3 relative">
              <button @click="removeProject(index)" class="absolute top-3 right-3 text-red-500 hover:text-red-700">
                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"/></svg>
              </button>
              <div class="grid sm:grid-cols-2 gap-3">
                <div class="sm:col-span-2">
                  <input v-model="proj.name" type="text" class="w-full px-3 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-primary-500 outline-none" placeholder="Project Name" />
                </div>
                <div>
                  <input v-model="proj.fundingAgency" type="text" class="w-full px-3 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-primary-500 outline-none" placeholder="Funding Agency / Institution" />
                </div>
                <div>
                  <input v-model="proj.duration" type="text" class="w-full px-3 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-primary-500 outline-none" placeholder="Duration (e.g. Jan 2020 - Dec 2022)" />
                </div>
                <div class="sm:col-span-2">
                  <textarea v-model="proj.description" rows="2" class="w-full px-3 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-primary-500 outline-none resize-none" placeholder="Project description or abstract..."></textarea>
                </div>
              </div>
            </div>
          </div>

          <!-- Proud Of -->
          <div class="mb-6">
            <div class="flex justify-between items-center mb-2">
              <label class="block text-sm font-medium text-slate-700">Most Proud Of</label>
              <button @click="addProudOf" class="px-3 py-1 bg-primary-600 text-white rounded-lg hover:bg-primary-700 transition text-xs font-medium">Add Achievement</button>
            </div>
            <div v-for="(po, index) in form.proudOf" :key="index" class="border border-slate-200 rounded-xl p-4 mb-3 relative">
              <button @click="removeProudOf(index)" class="absolute top-3 right-3 text-red-500 hover:text-red-700">
                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"/></svg>
              </button>
              <div class="grid gap-3">
                <input v-model="po.title" type="text" class="w-full px-3 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-primary-500 outline-none" placeholder="Achievement Title" />
                <textarea v-model="po.details" rows="2" class="w-full px-3 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-primary-500 outline-none resize-none" placeholder="Details about this achievement..."></textarea>
              </div>
            </div>
          </div>

          <!-- Publications -->
          <div class="mb-6">
            <label class="block text-sm font-medium text-slate-700 mb-2">Publications</label>
            <div class="space-y-4">
              <div>
                <label class="text-xs text-slate-500 mb-1 block">Books (one per line)</label>
                <textarea v-model="publicationsText.books" @input="updatePublications" rows="2" class="w-full px-3 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-primary-500 outline-none resize-none" placeholder="E. Someone, A Fictional Research. Publisher, 2010."></textarea>
              </div>
              <div>
                <label class="text-xs text-slate-500 mb-1 block">Journal Articles (one per line)</label>
                <textarea v-model="publicationsText.journalArticles" @input="updatePublications" rows="2" class="w-full px-3 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-primary-500 outline-none resize-none" placeholder="L. T. Wong, A non-existant paper, Journal Name, vol. 12, 2011."></textarea>
              </div>
              <div>
                <label class="text-xs text-slate-500 mb-1 block">Conference Proceedings (one per line)</label>
                <textarea v-model="publicationsText.conferenceProceedings" @input="updatePublications" rows="2" class="w-full px-3 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-primary-500 outline-none resize-none" placeholder="E. Someone, Another paper, Proceedings of Conference, 2013."></textarea>
              </div>
            </div>
          </div>

          <!-- Referees -->
          <div class="mb-6">
            <div class="flex justify-between items-center mb-2">
              <label class="block text-sm font-medium text-slate-700">Referees / References</label>
              <button @click="addReferee" class="px-3 py-1 bg-primary-600 text-white rounded-lg hover:bg-primary-700 transition text-xs font-medium">Add Referee</button>
            </div>
            <div v-for="(ref, index) in form.referees" :key="index" class="border border-slate-200 rounded-xl p-4 mb-3 relative">
              <button @click="removeReferee(index)" class="absolute top-3 right-3 text-red-500 hover:text-red-700">
                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"/></svg>
              </button>
              <div class="grid sm:grid-cols-2 gap-3">
                <div>
                  <input v-model="ref.name" type="text" class="w-full px-3 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-primary-500 outline-none" placeholder="Name (e.g. Prof. John Doe)" />
                </div>
                <div>
                  <input v-model="ref.institute" type="text" class="w-full px-3 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-primary-500 outline-none" placeholder="Institute / Organization" />
                </div>
                <div class="sm:col-span-2">
                  <input v-model="ref.email" type="email" class="w-full px-3 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-primary-500 outline-none" placeholder="Email" />
                </div>
                <div>
                  <input v-model="ref.addressLine1" type="text" class="w-full px-3 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-primary-500 outline-none" placeholder="Address Line 1" />
                </div>
                <div>
                  <input v-model="ref.addressLine2" type="text" class="w-full px-3 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-primary-500 outline-none" placeholder="Address Line 2" />
                </div>
              </div>
            </div>
          </div>
        </section>

        <!-- Step 7: Preview & Download -->
        <section v-else-if="currentStep === 6" key="preview" aria-label="Preview and Download">
          <h2 class="text-2xl font-bold text-slate-900 mb-6">Preview & Download</h2>
          
          <div class="bg-slate-50 rounded-xl p-6 mb-6">
            <h3 class="font-semibold text-slate-900 mb-4">Resume Summary</h3>
            <dl class="grid sm:grid-cols-2 gap-4 text-sm">
              <div><dt class="text-slate-500 inline">Name:</dt> <dd class="font-medium inline">{{ form.personalInfo.fullName || 'Not set' }}</dd></div>
              <div><dt class="text-slate-500 inline">Title:</dt> <dd class="font-medium inline">{{ form.personalInfo.jobTitle || 'Not set' }}</dd></div>
              <div><dt class="text-slate-500 inline">Experience:</dt> <dd class="font-medium inline">{{ form.experience.length }} entries</dd></div>
              <div><dt class="text-slate-500 inline">Education:</dt> <dd class="font-medium inline">{{ form.education.length }} entries</dd></div>
              <div><dt class="text-slate-500 inline">Skills:</dt> <dd class="font-medium inline">{{ form.skills.length }} skills</dd></div>
              <div><dt class="text-slate-500 inline">Template:</dt> <dd class="font-medium inline capitalize">{{ selectedTemplate }}</dd></div>
            </dl>
          </div>

          <div class="flex flex-col sm:flex-row gap-4">
            <button 
              @click="previewPdf"
              :disabled="isGenerating"
              class="flex-1 px-6 py-3 border-2 border-primary-600 text-primary-700 rounded-xl font-semibold hover:bg-primary-50 transition disabled:opacity-50 flex items-center justify-center gap-2"
              aria-label="Preview resume PDF"
            >
              <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"/><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z"/></svg>
              {{ isGenerating ? 'Generating...' : 'Preview PDF' }}
            </button>
            <button 
              @click="generatePdf"
              :disabled="isGenerating || (!canGenerate && !isPaid)"
              class="flex-1 px-6 py-3 bg-primary-600 text-white rounded-xl font-semibold hover:bg-primary-700 transition disabled:opacity-50 flex items-center justify-center gap-2"
              aria-label="Download resume PDF"
            >
              <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 16v1a3 3 0 003 3h10a3 3 0 003-3v-1m-4-4l-4 4m0 0l-4-4m4 4V4"/></svg>
              {{ isGenerating ? 'Generating...' : 'Download Resume PDF' }}
            </button>
          </div>

          <div v-if="!canGenerate && !isPaid" class="mt-4 p-4 bg-amber-50 border border-amber-200 rounded-lg" role="alert">
            <div class="flex items-start gap-3">
              <svg class="w-5 h-5 text-amber-600 mt-0.5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 10V3L4 14h7v7l9-11h-7z"/></svg>
              <div>
                <p class="text-amber-800 font-medium">You've used all your free resume generations.</p>
                <button @click="goToPricing" class="text-primary-600 font-semibold hover:underline mt-1">Upgrade to Unlimited for $9.99 →</button>
              </div>
            </div>
          </div>

          <div v-if="isPaid" class="mt-4 p-4 bg-green-50 border border-green-200 rounded-lg">
            <p class="text-green-700 text-sm font-medium flex items-center gap-2">
              <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"/></svg>
              Unlimited access active. Generate as many resumes as you need!
            </p>
          </div>

          <div v-if="pdfUrl" class="mt-6">
            <h3 class="font-semibold text-slate-900 mb-2">PDF Preview</h3>
            <iframe :src="pdfUrl" class="w-full h-[600px] border border-slate-200 rounded-lg" title="Resume PDF Preview"></iframe>
          </div>
        </section>
      </Transition>

      <!-- Navigation Buttons -->
      <nav class="flex justify-between mt-8 pt-6 border-t border-slate-200" aria-label="Form navigation">
        <button 
          v-if="currentStep > 0"
          @click="prevStep"
          class="px-6 py-2 border border-slate-300 text-slate-700 rounded-lg hover:bg-slate-50 transition font-medium"
        >
          Previous
        </button>
        <div v-else></div>
        
        <button 
          v-if="currentStep < steps.length - 1"
          @click="nextStep"
          class="px-6 py-2 bg-primary-600 text-white rounded-lg hover:bg-primary-700 transition font-medium"
        >
          Next Step
        </button>
      </nav>
    </div>
  </main>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useSeo } from '@/composables/useSeo.js'
import StepIndicator from '@/components/ui/StepIndicator.vue'
import { checkLimit, generateResume as apiGenerateResume, previewResume as apiPreviewResume } from '@/composables/useApi.js'

const props = defineProps({
  selectedTemplate: String,
  isPaid: Boolean
})

const emit = defineEmits(['go-home', 'go-pricing', 'update-usage'])

const steps = [
  { id: 'personal', label: 'Personal' },
  { id: 'summary', label: 'Summary' },
  { id: 'experience', label: 'Experience' },
  { id: 'education', label: 'Education' },
  { id: 'skills', label: 'Skills' },
  { id: 'additional', label: 'Additional' },
  { id: 'preview', label: 'Preview' }
]

const currentStep = ref(0)
const isGenerating = ref(false)
const canGenerate = ref(true)
const pdfUrl = ref(null)
const resumeId = ref(null)
const showPaymentModal = ref(false)

const form = reactive({
  template: props.selectedTemplate,
  personalInfo: {
    fullName: '',
    email: '',
    phone: '',
    address: '',
    city: '',
    country: '',
    linkedIn: '',
    website: '',
    jobTitle: '',
    twitter: '',
    github: '',
    orcid: '',
    gitlab: '',
    mastodon: ''
  },
  education: [],
  experience: [],
  skills: [],
  languages: [],
  summary: '',
  lifePhilosophy: '',
  projects: [],
  proudOf: [],
  publications: { books: [], journalArticles: [], conferenceProceedings: [] },
  referees: []
})

const skillsText = ref('')
const languagesText = ref('')

const updateSeoForStep = () => {
  const stepNames = ['Personal Information', 'Professional Summary', 'Work Experience', 'Education', 'Skills & Languages', 'Additional Info', 'Preview & Download']
  useSeo({
    title: `Step ${currentStep.value + 1}: ${stepNames[currentStep.value]}`,
    description: `Build your ${props.selectedTemplate} resume - ${stepNames[currentStep.value]} step.`,
    url: `https://resumeforge.app/builder#${steps[currentStep.value].id}`,
    noindex: true
  })
}

const checkUserLimit = async () => {
  try {
    const data = await checkLimit()
    canGenerate.value = data.canGenerate
  } catch (e) {
    console.error('Failed to check limit', e)
  }
}

const goToPricing = () => {
  showPaymentModal.value = false
  emit('go-pricing')
}

const addExperience = () => {
  form.experience.push({
    company: '',
    position: '',
    startDate: '',
    endDate: '',
    description: '',
    achievements: [],
    achievementsText: ''
  })
}

const removeExperience = (index) => {
  form.experience.splice(index, 1)
}

const updateAchievements = (exp) => {
  exp.achievements = exp.achievementsText
    .split('\n')
    .map(a => a.trim().replace(/^[-•]\s*/, ''))
    .filter(a => a)
}

const addEducation = () => {
  form.education.push({
    institution: '',
    degree: '',
    fieldOfStudy: '',
    startDate: '',
    endDate: '',
    description: ''
  })
}

const removeEducation = (index) => {
  form.education.splice(index, 1)
}

const updateSkills = () => {
  form.skills = skillsText.value
    .split(',')
    .map(s => s.trim())
    .filter(s => s)
}

const updateLanguages = () => {
  form.languages = languagesText.value
    .split(',')
    .map(l => l.trim())
    .filter(l => l)
}

const addProject = () => {
  form.projects.push({ name: '', fundingAgency: '', duration: '', description: '' })
}
const removeProject = (index) => {
  form.projects.splice(index, 1)
}

const addProudOf = () => {
  form.proudOf.push({ title: '', details: '' })
}
const removeProudOf = (index) => {
  form.proudOf.splice(index, 1)
}

const publicationsText = reactive({ books: '', journalArticles: '', conferenceProceedings: '' })
const updatePublications = () => {
  form.publications.books = publicationsText.books.split('\n').map(s => s.trim()).filter(s => s)
  form.publications.journalArticles = publicationsText.journalArticles.split('\n').map(s => s.trim()).filter(s => s)
  form.publications.conferenceProceedings = publicationsText.conferenceProceedings.split('\n').map(s => s.trim()).filter(s => s)
}

const addReferee = () => {
  form.referees.push({ name: '', institute: '', email: '', addressLine1: '', addressLine2: '' })
}
const removeReferee = (index) => {
  form.referees.splice(index, 1)
}

const goToStep = (index) => {
  if (index <= currentStep.value + 1) {
    currentStep.value = index
    updateSeoForStep()
  }
}

const nextStep = () => {
  if (currentStep.value < steps.length - 1) {
    currentStep.value++
    updateSeoForStep()
  }
}

const prevStep = () => {
  if (currentStep.value > 0) {
    currentStep.value--
    updateSeoForStep()
  }
}

const generatePayload = () => {
  return {
    template: props.selectedTemplate,
    personalInfo: { ...form.personalInfo },
    summary: form.summary,
    education: form.education.map(e => ({ ...e })),
    experience: form.experience.map(e => ({
      company: e.company,
      position: e.position,
      startDate: e.startDate,
      endDate: e.endDate,
      description: e.description,
      achievements: e.achievements
    })),
    skills: form.skills,
    languages: form.languages,
    lifePhilosophy: form.lifePhilosophy,
    projects: form.projects.map(p => ({ ...p })),
    proudOf: form.proudOf.map(p => ({ ...p })),
    publications: form.publications,
    referees: form.referees.map(r => ({ ...r }))
  }
}

const previewPdf = async () => {
  isGenerating.value = true
  pdfUrl.value = null
  try {
    const res = await apiPreviewResume(generatePayload())
    
    if (res.ok) {
      const blob = await res.blob()
      pdfUrl.value = URL.createObjectURL(blob)
      const newId = res.headers.get('X-Resume-Id')
      if (newId) resumeId.value = newId
    } else if (res.status === 429) {
      const data = await res.json().catch(() => ({}))
      canGenerate.value = false
      showPaymentModal.value = true
    } else {
      const data = await res.json().catch(() => ({}))
      alert(data.message || `Preview failed: ${res.status} ${res.statusText}`)
    }
  } catch (e) {
    console.error('Preview failed', e)
    alert('Failed to generate preview. Please try again.')
  } finally {
    isGenerating.value = false
  }
}

const generatePdf = async () => {
  isGenerating.value = true
  try {
    const res = await apiGenerateResume(generatePayload())
    
    if (res.status === 429) {
      const data = await res.json().catch(() => ({}))
      canGenerate.value = false
      showPaymentModal.value = true
      return
    }
    
    if (res.ok) {
      const blob = await res.blob()
      const url = URL.createObjectURL(blob)
      const a = document.createElement('a')
      a.href = url
      a.download = `${form.personalInfo.fullName || 'resume'}.pdf`
      document.body.appendChild(a)
      a.click()
      document.body.removeChild(a)
      URL.revokeObjectURL(url)
      
      const newId = res.headers.get('X-Resume-Id')
      if (newId) resumeId.value = newId
      
      const remaining = res.headers.get('X-Remaining')
      if (remaining !== null && !props.isPaid) {
        canGenerate.value = parseInt(remaining) > 0
      }
      emit('update-usage')
      alert('Resume downloaded successfully!')
    } else {
      const data = await res.json().catch(() => ({}))
      if (data.message && data.message.includes('upgrade')) {
        canGenerate.value = false
        showPaymentModal.value = true
      } else {
        alert(data.message || 'Failed to generate resume')
      }
    }
  } catch (e) {
    console.error('Generation failed', e)
    alert('Failed to generate resume. Please try again.')
  } finally {
    isGenerating.value = false
  }
}

const fillDemoData = () => {
  const template = props.selectedTemplate
  const isAcademic = template === 'academic'
  const isExecutive = template === 'executive'
  const isCreative = template === 'creative'
  const isLegal = template === 'classic'
  const isMedical = false

  // Personal Info
  const firstNames = ['Alex', 'Jordan', 'Taylor', 'Morgan', 'Casey', 'Riley', 'Quinn']
  const lastNames = ['Chen', 'Rodriguez', 'Patel', 'Kim', 'Singh', 'Okafor', 'Andersson']
  const fn = firstNames[Math.floor(Math.random() * firstNames.length)]
  const ln = lastNames[Math.floor(Math.random() * lastNames.length)]
  const fullName = isExecutive ? `${fn} ${ln}` : `${fn} ${ln}`
  const email = `${fn.toLowerCase()}.${ln.toLowerCase()}@email.com`

  let jobTitle, company, company2, summary

  if (isAcademic) {
    jobTitle = 'Postdoctoral Researcher'
    company = 'MIT CSAIL'
    company2 = 'Stanford AI Lab'
    summary = 'Research scientist with expertise in machine learning and natural language processing. Published 15+ papers in top-tier venues including NeurIPS, ICML, and ACL. Passionate about developing efficient AI systems that are accessible to everyone.'
  } else if (isExecutive) {
    jobTitle = 'Chief Technology Officer'
    company = 'TechVenture Inc.'
    company2 = 'Global Systems Corp'
    summary = 'Visionary technology leader with 15+ years of experience driving digital transformation for Fortune 500 companies. Proven track record of building high-performing engineering teams and delivering multi-million dollar technology initiatives on time and under budget.'
  } else if (isLegal) {
    jobTitle = 'Senior Associate Attorney'
    company = 'Skadden, Arps, Slate, Meagher & Flom LLP'
    company2 = 'Cravath, Swaine & Moore LLP'
    summary = 'Results-driven attorney with 8+ years of experience in corporate law, M&A transactions, and securities regulation. Successfully closed over $2B in transactions and advised clients on complex cross-border deals across 12 jurisdictions.'
  } else if (isCreative) {
    jobTitle = 'Senior Art Director'
    company = 'Pentagram Design'
    company2 = 'IDEO'
    summary = 'Award-winning creative director with a passion for crafting visually compelling brand experiences. 10+ years leading design teams for global brands including Nike, Apple, and Spotify. Expert in visual identity, motion design, and interactive media.'
  } else {
    jobTitle = 'Senior Software Engineer'
    company = 'Google'
    company2 = 'Stripe'
    summary = 'Full-stack engineer with 6+ years building scalable web applications serving millions of users. Expert in distributed systems, cloud architecture, and modern JavaScript frameworks. Passionate about clean code, developer experience, and open source.'
  }

  form.personalInfo.fullName = fullName
  form.personalInfo.jobTitle = jobTitle
  form.personalInfo.email = email
  form.personalInfo.phone = '+1 (555) 123-4567'
  form.personalInfo.city = isAcademic ? 'Cambridge' : (isExecutive ? 'New York' : 'San Francisco')
  form.personalInfo.country = 'USA'
  form.personalInfo.address = '123 Innovation Drive, Suite 400'
  form.personalInfo.linkedIn = `linkedin.com/in/${fn.toLowerCase()}-${ln.toLowerCase()}`
  form.personalInfo.website = `${fn.toLowerCase()}${ln.toLowerCase()}.dev`
  form.personalInfo.twitter = `@${fn.toLowerCase()}${ln.charAt(0)}`
  form.personalInfo.github = `${fn.toLowerCase()}${ln.toLowerCase()}`
  form.personalInfo.orcid = isAcademic ? '0000-0001-2345-6789' : ''
  form.personalInfo.gitlab = isAcademic ? `${fn.toLowerCase()}-lab` : ''
  form.personalInfo.mastodon = ''

  // Summary
  form.summary = summary

  // Experience
  form.experience = []
  const exp1 = {
    company: company,
    position: jobTitle,
    startDate: 'Jan 2021',
    endDate: 'Present',
    description: isAcademic
      ? 'Leading research on efficient transformer architectures and large language model optimization. Managing a team of 3 PhD students and 2 research assistants.'
      : (isExecutive
        ? 'Leading a 120-person engineering organization across 4 global offices. Responsible for product strategy, technical roadmap, and $50M annual budget.'
        : 'Lead engineer responsible for core platform architecture serving 10M+ daily active users. Mentored 5 junior engineers and drove adoption of microservices.'),
    achievements: isAcademic
      ? ['Published 5 papers at NeurIPS and ICML', 'Secured $500K NSF research grant', 'Open-sourced library with 10K+ GitHub stars']
      : (isExecutive
        ? ['Grew engineering team from 40 to 120 in 18 months', 'Reduced infrastructure costs by 35% through cloud optimization', 'Launched 3 new product lines generating $12M ARR']
        : ['Reduced API latency by 60% through caching optimization', 'Led migration from monolith to microservices', 'Built real-time data pipeline processing 1M events/sec']),
    achievementsText: ''
  }
  exp1.achievementsText = exp1.achievements.map(a => `- ${a}`).join('\n')

  const exp2 = {
    company: company2,
    position: isAcademic ? 'Research Assistant' : (isExecutive ? 'VP of Engineering' : 'Software Engineer'),
    startDate: 'Jun 2018',
    endDate: 'Dec 2020',
    description: isAcademic
      ? 'Conducted research on natural language understanding and semantic parsing under supervision of Prof. Yann LeCun.'
      : (isExecutive
        ? 'Led engineering teams building core platform infrastructure. Scaled systems to handle 100x traffic growth during product launch.'
        : 'Full-stack development of customer-facing web applications using React, Node.js, and PostgreSQL. Collaborated with design and product teams.'),
    achievements: isAcademic
      ? ['Co-authored 3 journal publications', 'Developed novel attention mechanism improving BLEU scores by 15%']
      : (isExecutive
        ? ['Built engineering team from scratch to 40 engineers', 'Achieved 99.99% uptime SLA across all services']
        : ['Shipped feature generating $2M additional ARR', 'Reduced page load time from 4s to 800ms']),
    achievementsText: ''
  }
  exp2.achievementsText = exp2.achievements.map(a => `- ${a}`).join('\n')

  form.experience.push(exp1, exp2)

  // Education
  form.education = []
  if (isAcademic) {
    form.education.push({
      institution: 'Stanford University',
      degree: 'Ph.D.',
      fieldOfStudy: 'Computer Science',
      startDate: '2014',
      endDate: '2018',
      description: 'Thesis: Efficient Attention Mechanisms for Large-Scale Language Models. Advisor: Prof. Christopher Manning. GPA: 3.95/4.0'
    })
    form.education.push({
      institution: 'MIT',
      degree: 'M.S.',
      fieldOfStudy: 'Electrical Engineering & Computer Science',
      startDate: '2012',
      endDate: '2014',
      description: 'Focus on Artificial Intelligence and Machine Learning. Graduated with Honors.'
    })
  } else if (isLegal) {
    form.education.push({
      institution: 'Harvard Law School',
      degree: 'J.D.',
      fieldOfStudy: 'Law',
      startDate: '2012',
      endDate: '2015',
      description: 'Cum Laude. Harvard Law Review, Articles Editor. Moot Court Champion.'
    })
    form.education.push({
      institution: 'Yale University',
      degree: 'B.A.',
      fieldOfStudy: 'Economics & Political Science',
      startDate: '2008',
      endDate: '2012',
      description: 'Magna Cum Laude. Phi Beta Kappa. Debate Team Captain.'
    })
  } else {
    form.education.push({
      institution: 'Carnegie Mellon University',
      degree: 'B.S.',
      fieldOfStudy: 'Computer Science',
      startDate: '2014',
      endDate: '2018',
      description: isAcademic ? 'GPA: 3.9/4.0. Research Assistant in NLP Lab.' : 'Dean\'s List. ACM Programming Contest Finalist.'
    })
    form.education.push({
      institution: 'UC Berkeley',
      degree: 'M.S.',
      fieldOfStudy: isAcademic ? 'Artificial Intelligence' : 'Software Engineering',
      startDate: '2018',
      endDate: '2020',
      description: 'Focus on distributed systems and machine learning.'
    })
  }

  // Skills
  if (isAcademic) {
    form.skills = ['Python', 'PyTorch', 'TensorFlow', 'NLP', 'Deep Learning', 'LaTeX', 'CUDA', 'Distributed Training']
    skillsText.value = form.skills.join(', ')
  } else if (isLegal) {
    form.skills = ['M&A Transactions', 'Securities Regulation', 'Contract Negotiation', 'Due Diligence', 'Cross-border Deals', 'Legal Research', 'Client Relations']
    skillsText.value = form.skills.join(', ')
  } else if (isExecutive) {
    form.skills = ['Strategic Planning', 'Team Leadership', 'P&L Management', 'Digital Transformation', 'Stakeholder Management', 'Agile/Scrum', 'Cloud Architecture']
    skillsText.value = form.skills.join(', ')
  } else if (isCreative) {
    form.skills = ['Adobe Creative Suite', 'Figma', 'Motion Design', 'Brand Identity', 'Typography', 'Art Direction', 'Prototyping']
    skillsText.value = form.skills.join(', ')
  } else {
    form.skills = ['JavaScript', 'TypeScript', 'React', 'Node.js', 'Python', 'AWS', 'Docker', 'PostgreSQL', 'GraphQL']
    skillsText.value = form.skills.join(', ')
  }

  // Languages
  form.languages = ['English (Native)', 'Spanish (Fluent)', 'Mandarin (Conversational)']
  languagesText.value = form.languages.join(', ')

  // Academic-specific fields
  if (isAcademic) {
    form.lifePhilosophy = 'Science is not only a disciple of reason but, also, one of romance and passion.'

    form.projects = [
      {
        name: 'OpenEfficientNLP',
        fundingAgency: 'NSF Grant #IIS-2045678',
        duration: '2022 - 2024',
        description: 'Open-source library for efficient NLP model inference. Achieved 3x speedup over baseline with no accuracy loss.'
      },
      {
        name: 'GreenAI Initiative',
        fundingAgency: 'EU Horizon 2020',
        duration: '2021 - 2023',
        description: 'Research project focused on reducing carbon footprint of large AI models through efficient training techniques.'
      }
    ]

    form.proudOf = [
      { title: 'NeurIPS Best Paper Award 2023', details: 'Recognized for breakthrough work on sub-quadratic attention mechanisms.' },
      { title: 'Open Source Contributor (50K+ stars)', details: 'Core maintainer of popular ML libraries used by thousands of researchers worldwide.' }
    ]

    form.publications = {
      books: ['J. Smith, Efficient Deep Learning. Academic Press, 2024.'],
      journalArticles: [
        'J. Smith et al., Fast Attention for Long Sequences, JMLR, vol. 25, 2023.',
        'J. Smith, A Survey of Efficient Transformers, AI Review, vol. 12, 2022.'
      ],
      conferenceProceedings: [
        'J. Smith and A. Doe, FlashAttention-3, NeurIPS 2023.',
        'J. Smith et al., Memory-Efficient LLMs, ICML 2022.',
        'J. Smith, Sparse Transformers, ACL 2021.'
      ]
    }
    publicationsText.books = form.publications.books.join('\n')
    publicationsText.journalArticles = form.publications.journalArticles.join('\n')
    publicationsText.conferenceProceedings = form.publications.conferenceProceedings.join('\n')

    form.referees = [
      {
        name: 'Prof. Yann LeCun',
        institute: 'NYU / Meta AI',
        email: 'yann@nyu.edu',
        addressLine1: '60 Fifth Avenue',
        addressLine2: 'New York, NY 10011'
      },
      {
        name: 'Prof. Geoffrey Hinton',
        institute: 'University of Toronto',
        email: 'hinton@cs.toronto.edu',
        addressLine1: "6 King's College Rd",
        addressLine2: 'Toronto, ON M5S 3G4'
      }
    ]
  }
}

onMounted(() => {
  checkUserLimit()
  if (form.experience.length === 0) addExperience()
  if (form.education.length === 0) addEducation()
  updateSeoForStep()
})
</script>

<style scoped>
.fade-enter-active, .fade-leave-active {
  transition: opacity 0.3s ease;
}
.fade-enter-from, .fade-leave-to {
  opacity: 0;
}
</style>

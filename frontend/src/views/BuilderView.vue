<template>
  <main class="max-w-4xl mx-auto px-4 py-8">
    <StepIndicator :steps="steps" :current-step="currentStep" @go-to-step="goToStep" />

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

        <!-- Step 6: Preview & Download -->
        <section v-else-if="currentStep === 5" key="preview" aria-label="Preview and Download">
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
    jobTitle: ''
  },
  education: [],
  experience: [],
  skills: [],
  languages: [],
  summary: ''
})

const skillsText = ref('')
const languagesText = ref('')

const updateSeoForStep = () => {
  const stepNames = ['Personal Information', 'Professional Summary', 'Work Experience', 'Education', 'Skills & Languages', 'Preview & Download']
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
    languages: form.languages
  }
}

const previewPdf = async () => {
  isGenerating.value = true
  try {
    const res = await apiPreviewResume(generatePayload())
    
    if (res.ok) {
      const blob = await res.blob()
      pdfUrl.value = URL.createObjectURL(blob)
      const newId = res.headers.get('X-Resume-Id')
      if (newId) resumeId.value = newId
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
      const data = await res.json()
      canGenerate.value = false
      showPaymentModal.value = true
      return
    }
    
    if (res.ok) {
      const blob = await res.blob()
      const url = URL.createObjectURL(blob)
      const a = document.createElement('a')
      a.href = url
      a.download = 'resume.pdf'
      a.click()
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
      const data = await res.json()
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

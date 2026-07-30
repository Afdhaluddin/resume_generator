<template>
  <main class="max-w-4xl mx-auto px-4 py-12">
    <div class="text-center mb-12">
      <h1 class="text-4xl font-bold text-slate-900 mb-4">Simple, Transparent Pricing</h1>
      <p class="text-lg text-slate-600 max-w-xl mx-auto">
        Start free with 2 resumes. Upgrade once and create unlimited resumes forever.
      </p>
    </div>

    <div class="grid md:grid-cols-2 gap-8 max-w-3xl mx-auto">
      <!-- Free Plan -->
      <div class="bg-white rounded-2xl shadow-lg p-8 border border-slate-200">
        <h2 class="text-2xl font-bold text-slate-900 mb-2">Free</h2>
        <p class="text-slate-600 mb-6">Perfect for trying out ResumeForge</p>
        <div class="mb-6">
          <span class="text-4xl font-bold text-slate-900">$0</span>
          <span class="text-slate-500">/forever</span>
        </div>
        <ul class="space-y-3 mb-8">
          <li class="flex items-center gap-3 text-slate-700">
            <svg class="w-5 h-5 text-green-500" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"/></svg>
            2 resume generations
          </li>
          <li class="flex items-center gap-3 text-slate-700">
            <svg class="w-5 h-5 text-green-500" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"/></svg>
            All 3 templates
          </li>
          <li class="flex items-center gap-3 text-slate-700">
            <svg class="w-5 h-5 text-green-500" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"/></svg>
            PDF export
          </li>
          <li class="flex items-center gap-3 text-slate-400">
            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"/></svg>
            No editing after generation
          </li>
        </ul>
        <button 
          @click="$emit('go-home')"
          class="w-full py-3 border-2 border-primary-600 text-primary-700 rounded-xl font-semibold hover:bg-primary-50 transition"
        >
          Get Started Free
        </button>
      </div>

      <!-- Unlimited Plan -->
      <div class="bg-white rounded-2xl shadow-xl p-8 border-2 border-primary-500 relative">
        <div class="absolute -top-3 left-1/2 -translate-x-1/2 px-4 py-1 bg-primary-600 text-white text-sm font-semibold rounded-full">
          Most Popular
        </div>
        <h2 class="text-2xl font-bold text-slate-900 mb-2">Unlimited</h2>
        <p class="text-slate-600 mb-6">For serious job seekers</p>
        <div class="mb-6">
          <span class="text-4xl font-bold text-slate-900">$9.99</span>
          <span class="text-slate-500">/one-time</span>
        </div>
        <ul class="space-y-3 mb-8">
          <li class="flex items-center gap-3 text-slate-700">
            <svg class="w-5 h-5 text-green-500" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"/></svg>
            <strong>Unlimited</strong> resume generations
          </li>
          <li class="flex items-center gap-3 text-slate-700">
            <svg class="w-5 h-5 text-green-500" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"/></svg>
            All 3 templates
          </li>
          <li class="flex items-center gap-3 text-slate-700">
            <svg class="w-5 h-5 text-green-500" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"/></svg>
            PDF export
          </li>
          <li class="flex items-center gap-3 text-slate-700">
            <svg class="w-5 h-5 text-green-500" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"/></svg>
            Edit & re-download anytime
          </li>
          <li class="flex items-center gap-3 text-slate-700">
            <svg class="w-5 h-5 text-green-500" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"/></svg>
            Priority support
          </li>
        </ul>
        <button 
          @click="startCheckout"
          :disabled="isLoading"
          class="w-full py-3 bg-primary-600 text-white rounded-xl font-semibold hover:bg-primary-700 transition disabled:opacity-50 flex items-center justify-center gap-2"
        >
          <svg v-if="isLoading" class="w-5 h-5 animate-spin" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15"/></svg>
          {{ isLoading ? 'Loading...' : 'Upgrade to Unlimited' }}
        </button>
        <p class="text-xs text-slate-500 text-center mt-3">Secure payment via Stripe. No subscription.</p>
      </div>
    </div>

    <!-- FAQ -->
    <section class="mt-16 max-w-2xl mx-auto">
      <h2 class="text-2xl font-bold text-slate-900 text-center mb-8">Frequently Asked Questions</h2>
      <div class="space-y-4">
        <details class="bg-white rounded-lg p-4 shadow-sm border border-slate-200">
          <summary class="font-semibold text-slate-900 cursor-pointer">What happens after I pay?</summary>
          <p class="text-slate-600 mt-2 text-sm">You'll get unlimited access immediately. Just use the same email you paid with, and the limit counter disappears.</p>
        </details>
        <details class="bg-white rounded-lg p-4 shadow-sm border border-slate-200">
          <summary class="font-semibold text-slate-900 cursor-pointer">Is this a subscription?</summary>
          <p class="text-slate-600 mt-2 text-sm">No. It's a one-time payment of $9.99. Pay once, use forever.</p>
        </details>
        <details class="bg-white rounded-lg p-4 shadow-sm border border-slate-200">
          <summary class="font-semibold text-slate-900 cursor-pointer">Can I get a refund?</summary>
          <p class="text-slate-600 mt-2 text-sm">Contact us within 7 days if you're not satisfied. We'll process a full refund, no questions asked.</p>
        </details>
      </div>
    </section>
  </main>
</template>

<script setup>
import { ref } from 'vue'
import { createCheckoutSession } from '@/composables/useApi.js'

const isLoading = ref(false)
const email = ref('')

defineEmits(['go-home'])

const startCheckout = async () => {
  // Prompt for email
  const userEmail = prompt('Enter your email address for the checkout:')
  if (!userEmail || !userEmail.includes('@')) {
    alert('Please enter a valid email address.')
    return
  }
  email.value = userEmail
  isLoading.value = true

  try {
    const data = await createCheckoutSession(userEmail)
    // Store email for after payment
    localStorage.setItem('pendingEmail', userEmail)
    // Redirect to Stripe checkout
    window.location.href = data.url
  } catch (e) {
    alert(e.message || 'Failed to start checkout. Please try again.')
  } finally {
    isLoading.value = false
  }
}
</script>

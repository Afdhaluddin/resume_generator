<template>
  <header class="bg-white shadow-sm border-b border-slate-200 sticky top-0 z-50">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <div class="flex justify-between items-center h-16">
        <div class="flex items-center gap-2 cursor-pointer" @click="$emit('go-home')">
          <div class="w-8 h-8 bg-primary-600 rounded-lg flex items-center justify-center">
            <svg class="w-5 h-5 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"/>
            </svg>
          </div>
          <span class="text-xl font-bold text-slate-900">ResumeForge</span>
        </div>
        <div class="flex items-center gap-4">
          <button 
            v-if="!isPaid"
            @click="$emit('go-pricing')"
            class="hidden sm:flex items-center gap-1.5 px-3 py-1.5 bg-amber-100 text-amber-700 rounded-lg text-sm font-semibold hover:bg-amber-200 transition"
          >
            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 10V3L4 14h7v7l9-11h-7z"/></svg>
            Go Unlimited
          </button>
          <div v-if="usageInfo && !isPaid" class="hidden sm:flex items-center gap-2 text-sm">
            <span class="text-slate-600">Free:</span>
            <span class="font-semibold" :class="usageInfo.remaining > 0 ? 'text-green-600' : 'text-red-600'">
              {{ usageInfo.remaining === 2147483647 ? 'Unlimited' : usageInfo.remaining }} / {{ usageInfo.limit === 2147483647 ? 'Unlimited' : usageInfo.limit }}
            </span>
          </div>
          <div v-else-if="isPaid" class="hidden sm:flex items-center gap-1.5 text-sm text-green-700 font-semibold">
            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"/></svg>
            Unlimited
          </div>
          <a 
            href="#admin"
            class="hidden sm:flex items-center gap-1.5 text-sm text-slate-500 hover:text-slate-900 transition"
            title="Admin Dashboard"
          >
            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z"/></svg>
            Admin
          </a>
          <button 
            v-if="showBackButton"
            @click="$emit('go-home')"
            class="text-slate-600 hover:text-slate-900 text-sm font-medium"
          >
            Back to Home
          </button>
        </div>
      </div>
    </div>
  </header>
</template>

<script setup>
defineProps({
  usageInfo: Object,
  showBackButton: Boolean,
  isPaid: Boolean
})

defineEmits(['go-home', 'go-pricing'])
</script>

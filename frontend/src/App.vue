<template>
  <div class="min-h-screen flex flex-col bg-slate-50">
    <AppHeader 
      :usage-info="usageInfo"
      :show-back-button="currentView !== 'home'"
      :is-paid="isPaid"
      @go-home="currentView = 'home'"
      @go-pricing="currentView = 'pricing'"
    />

    <main class="flex-1">
      <HomeView 
        v-if="currentView === 'home'" 
        @select-template="selectTemplate"
        @go-pricing="currentView = 'pricing'"
        :usage-info="usageInfo"
      />
      <BuilderView 
        v-else-if="currentView === 'builder'"
        :selected-template="selectedTemplate"
        @go-home="currentView = 'home'"
        @go-pricing="currentView = 'pricing'"
        @update-usage="fetchUsage"
        :is-paid="isPaid"
      />
      <PricingView
        v-else-if="currentView === 'pricing'"
        @go-home="currentView = 'home'"
      />
      <AdminView
        v-else-if="currentView === 'admin'"
      />
    </main>

    <AppFooter />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import AppHeader from './components/layout/AppHeader.vue'
import AppFooter from './components/layout/AppFooter.vue'
import HomeView from './views/HomeView.vue'
import BuilderView from './views/BuilderView.vue'
import PricingView from './views/PricingView.vue'
import AdminView from './views/AdminView.vue'
import { checkLimit, checkPaymentStatus } from './composables/useApi.js'

const currentView = ref('home')
const selectedTemplate = ref('modern')
const usageInfo = ref(null)
const isPaid = ref(false)

const selectTemplate = (template) => {
  selectedTemplate.value = template
  currentView.value = 'builder'
}

const fetchUsage = async () => {
  try {
    const data = await checkLimit()
    usageInfo.value = data
    isPaid.value = data.limit === 2147483647
  } catch (e) {
    console.error('Failed to fetch usage', e)
  }
}

const verifyPayment = async () => {
  const urlParams = new URLSearchParams(window.location.search)
  const sessionId = urlParams.get('session_id')
  const pendingEmail = localStorage.getItem('pendingEmail')

  if (sessionId && pendingEmail) {
    try {
      const status = await checkPaymentStatus(pendingEmail)
      if (status.isPaid) {
        localStorage.setItem('customerEmail', pendingEmail)
        localStorage.removeItem('pendingEmail')
        isPaid.value = true
        window.history.replaceState({}, document.title, window.location.pathname)
        alert('Payment successful! You now have unlimited access.')
      }
    } catch (e) {
      console.error('Payment verification failed', e)
    }
  }

  const storedEmail = localStorage.getItem('customerEmail')
  if (storedEmail && !isPaid.value) {
    try {
      const status = await checkPaymentStatus(storedEmail)
      isPaid.value = status.isPaid
    } catch (e) {
      console.error('Payment status check failed', e)
    }
  }
}

// Check for admin route via hash
const checkRoute = () => {
  const hash = window.location.hash
  if (hash === '#admin') {
    currentView.value = 'admin'
  }
}

onMounted(() => {
  fetchUsage()
  verifyPayment()
  checkRoute()
  window.addEventListener('hashchange', checkRoute)
})
</script>

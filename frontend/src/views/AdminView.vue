<template>
  <main class="max-w-6xl mx-auto px-4 py-8">
    <header class="mb-8">
      <h1 class="text-3xl font-bold text-slate-900">Admin Dashboard</h1>
      <p class="text-slate-600 mt-1">Payment statistics and customer overview</p>
    </header>

    <!-- Stats Cards -->
    <div class="grid sm:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
      <div class="bg-white rounded-xl shadow-sm border border-slate-200 p-6">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-sm font-medium text-slate-500">Total Customers</p>
            <p class="text-3xl font-bold text-slate-900 mt-1">{{ stats.totalCustomers }}</p>
          </div>
          <div class="w-12 h-12 bg-green-100 rounded-xl flex items-center justify-center">
            <svg class="w-6 h-6 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0z"/></svg>
          </div>
        </div>
      </div>

      <div class="bg-white rounded-xl shadow-sm border border-slate-200 p-6">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-sm font-medium text-slate-500">Total Revenue</p>
            <p class="text-3xl font-bold text-slate-900 mt-1">{{ stats.totalRevenue }}</p>
          </div>
          <div class="w-12 h-12 bg-blue-100 rounded-xl flex items-center justify-center">
            <svg class="w-6 h-6 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1M21 12a9 9 0 11-18 0 9 9 0 0118 0z"/></svg>
          </div>
        </div>
      </div>

      <div class="bg-white rounded-xl shadow-sm border border-slate-200 p-6">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-sm font-medium text-slate-500">Today's Customers</p>
            <p class="text-3xl font-bold text-slate-900 mt-1">{{ stats.todaysCustomers }}</p>
          </div>
          <div class="w-12 h-12 bg-amber-100 rounded-xl flex items-center justify-center">
            <svg class="w-6 h-6 text-amber-600" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z"/></svg>
          </div>
        </div>
      </div>

      <div class="bg-white rounded-xl shadow-sm border border-slate-200 p-6">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-sm font-medium text-slate-500">Today's Revenue</p>
            <p class="text-3xl font-bold text-slate-900 mt-1">{{ stats.todaysRevenue }}</p>
          </div>
          <div class="w-12 h-12 bg-purple-100 rounded-xl flex items-center justify-center">
            <svg class="w-6 h-6 text-purple-600" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 7h8m0 0v8m0-8l-8 8-4-4-6 6"/></svg>
          </div>
        </div>
      </div>
    </div>

    <!-- Payments Table -->
    <div class="bg-white rounded-xl shadow-sm border border-slate-200 overflow-hidden">
      <div class="px-6 py-4 border-b border-slate-200 flex justify-between items-center">
        <h2 class="text-lg font-semibold text-slate-900">Payment History</h2>
        <button 
          @click="fetchData"
          :disabled="isLoading"
          class="text-sm text-primary-600 hover:text-primary-700 font-medium flex items-center gap-1 disabled:opacity-50"
        >
          <svg v-if="isLoading" class="w-4 h-4 animate-spin" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15"/></svg>
          <svg v-else class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15"/></svg>
          Refresh
        </button>
      </div>

      <div v-if="isLoading && payments.length === 0" class="p-12 text-center">
        <svg class="w-8 h-8 animate-spin text-slate-400 mx-auto mb-3" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15"/></svg>
        <p class="text-slate-500">Loading payments...</p>
      </div>

      <div v-else-if="payments.length === 0" class="p-12 text-center">
        <div class="w-16 h-16 bg-slate-100 rounded-full flex items-center justify-center mx-auto mb-4">
          <svg class="w-8 h-8 text-slate-400" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"/></svg>
        </div>
        <p class="text-slate-500">No payments yet.</p>
        <p class="text-slate-400 text-sm mt-1">Payments will appear here when customers complete checkout.</p>
      </div>

      <table v-else class="w-full text-left">
        <thead class="bg-slate-50">
          <tr>
            <th class="px-6 py-3 text-xs font-semibold text-slate-500 uppercase tracking-wider">Email</th>
            <th class="px-6 py-3 text-xs font-semibold text-slate-500 uppercase tracking-wider">Amount</th>
            <th class="px-6 py-3 text-xs font-semibold text-slate-500 uppercase tracking-wider">Date</th>
            <th class="px-6 py-3 text-xs font-semibold text-slate-500 uppercase tracking-wider">Session ID</th>
          </tr>
        </thead>
        <tbody class="divide-y divide-slate-200">
          <tr v-for="payment in payments" :key="payment.sessionId" class="hover:bg-slate-50 transition">
            <td class="px-6 py-4 text-sm text-slate-900 font-medium">{{ payment.email }}</td>
            <td class="px-6 py-4 text-sm text-green-700 font-semibold">{{ payment.amount }}</td>
            <td class="px-6 py-4 text-sm text-slate-600">{{ formatDate(payment.paidAt) }}</td>
            <td class="px-6 py-4 text-sm text-slate-400 font-mono text-xs">{{ payment.sessionId }}</td>
          </tr>
        </tbody>
      </table>
    </div>

    <div class="mt-8 p-4 bg-blue-50 border border-blue-200 rounded-lg">
      <p class="text-blue-800 text-sm">
        <strong>Note:</strong> This dashboard shows data stored in memory. On server restart, payment history is reset. For production, connect to a database.
      </p>
    </div>
  </main>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useSeo } from '@/composables/useSeo.js'

const API_BASE = '/api'

const stats = ref({
  totalCustomers: 0,
  totalRevenue: '$0.00',
  todaysCustomers: 0,
  todaysRevenue: '$0.00'
})
const payments = ref([])
const isLoading = ref(false)

const fetchStats = async () => {
  try {
    const res = await fetch(`${API_BASE}/admin/stats`)
    if (res.ok) stats.value = await res.json()
  } catch (e) {
    console.error('Failed to fetch stats', e)
  }
}

const fetchPayments = async () => {
  try {
    const res = await fetch(`${API_BASE}/admin/payments`)
    if (res.ok) payments.value = await res.json()
  } catch (e) {
    console.error('Failed to fetch payments', e)
  }
}

const fetchData = async () => {
  isLoading.value = true
  await Promise.all([fetchStats(), fetchPayments()])
  isLoading.value = false
}

const formatDate = (isoString) => {
  if (!isoString) return '—'
  const d = new Date(isoString)
  return d.toLocaleString('en-US', {
    month: 'short',
    day: 'numeric',
    year: 'numeric',
    hour: 'numeric',
    minute: '2-digit'
  })
}

onMounted(() => {
  useSeo({
    title: 'Admin Dashboard',
    description: 'ResumeForge admin dashboard - view payment statistics and customer data.',
    noindex: true
  })
  fetchData()
})
</script>

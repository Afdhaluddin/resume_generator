import { API } from '@/constants/api.js'

function getHeaders() {
  const headers = { 'Content-Type': 'application/json' }
  const email = localStorage.getItem('customerEmail')
  if (email) {
    headers['X-Customer-Email'] = email
  }
  return headers
}

export async function checkLimit() {
  const res = await fetch(API.CHECK_LIMIT, {
    method: 'GET',
    headers: getHeaders()
  })
  if (!res.ok) throw new Error('Failed to check limit')
  return res.json()
}

export async function generateResume(data) {
  const res = await fetch(API.GENERATE, {
    method: 'POST',
    headers: getHeaders(),
    body: JSON.stringify(data)
  })
  return res
}

export async function previewResume(data) {
  const res = await fetch(API.PREVIEW, {
    method: 'POST',
    headers: getHeaders(),
    body: JSON.stringify(data)
  })
  return res
}

export async function createCheckoutSession(email) {
  const res = await fetch(API.CREATE_CHECKOUT, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ email })
  })
  if (!res.ok) {
    const err = await res.json().catch(() => ({}))
    throw new Error(err.error || 'Failed to create checkout session')
  }
  return res.json()
}

export async function checkPaymentStatus(email) {
  const res = await fetch(API.PAYMENT_STATUS(email))
  if (!res.ok) throw new Error('Failed to check payment status')
  return res.json()
}

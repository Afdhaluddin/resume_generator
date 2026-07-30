const BASE_URL = import.meta.env.VITE_API_BASE_URL || '/api'

export const API = {
  CHECK_LIMIT: `${BASE_URL}/resume/check-limit`,
  GENERATE: `${BASE_URL}/resume/generate`,
  PREVIEW: `${BASE_URL}/resume/preview`,
  GET_RESUME: (id) => `${BASE_URL}/resume/${id}`,
  UPDATE_RESUME: (id) => `${BASE_URL}/resume/${id}`,
  CREATE_CHECKOUT: `${BASE_URL}/payment/create-checkout-session`,
  PAYMENT_STATUS: (email) => `${BASE_URL}/payment/status?email=${encodeURIComponent(email)}`,
  ADMIN_STATS: `${BASE_URL}/admin/stats`,
  ADMIN_PAYMENTS: `${BASE_URL}/admin/payments`
}

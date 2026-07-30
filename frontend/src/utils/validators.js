export function isValidEmail(email) {
  return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)
}

export function isNotEmpty(value) {
  return value && value.trim().length > 0
}

export function minLength(value, length) {
  return value && value.trim().length >= length
}

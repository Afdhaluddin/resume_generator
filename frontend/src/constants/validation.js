export const VALIDATION_RULES = {
  fullName: { required: true, minLength: 2, message: 'Full name is required' },
  email: { required: true, pattern: /^[^\s@]+@[^\s@]+\.[^\s@]+$/, message: 'Valid email is required' },
  jobTitle: { required: true, minLength: 2, message: 'Job title is required' },
  company: { required: false, minLength: 1, message: 'Company name is required' },
  position: { required: false, minLength: 1, message: 'Position is required' },
  institution: { required: false, minLength: 1, message: 'Institution is required' },
  degree: { required: false, minLength: 1, message: 'Degree is required' }
}

export function validateField(value, rules) {
  if (rules.required && (!value || value.trim().length === 0)) {
    return rules.message
  }
  if (value && rules.minLength && value.trim().length < rules.minLength) {
    return `Minimum ${rules.minLength} characters required`
  }
  if (value && rules.pattern && !rules.pattern.test(value)) {
    return rules.message
  }
  return null
}

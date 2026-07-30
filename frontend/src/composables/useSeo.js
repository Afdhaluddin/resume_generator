/**
 * SEO composable for dynamic meta tag management in Vue 3
 * Usage: useSeo({ title: 'Page Title', description: '...', ... })
 */

const DEFAULT_TITLE = 'ResumeForge'
const DEFAULT_DESCRIPTION = 'Build professional, ATS-friendly resumes in minutes with ResumeForge. Free for first 2 resumes.'
const DEFAULT_URL = 'https://resumeforge.app/'
const DEFAULT_IMAGE = 'https://resumeforge.app/og-image.png'

export function useSeo(options = {}) {
  const {
    title = DEFAULT_TITLE,
    description = DEFAULT_DESCRIPTION,
    url = DEFAULT_URL,
    image = DEFAULT_IMAGE,
    type = 'website',
    noindex = false
  } = options

  const fullTitle = title === DEFAULT_TITLE 
    ? title 
    : `${title} | ${DEFAULT_TITLE}`

  // Update document title
  document.title = fullTitle

  // Helper to update or create meta tag
  const setMeta = (selector, attr, value, content) => {
    let el = document.querySelector(selector)
    if (!el) {
      el = document.createElement('meta')
      el.setAttribute(attr, value)
      document.head.appendChild(el)
    }
    el.setAttribute('content', content)
  }

  // Standard meta tags
  setMeta('meta[name="title"]', 'name', 'title', fullTitle)
  setMeta('meta[name="description"]', 'name', 'description', description)
  setMeta('meta[name="robots"]', 'name', 'robots', noindex ? 'noindex, nofollow' : 'index, follow')

  // Open Graph
  setMeta('meta[property="og:title"]', 'property', 'og:title', fullTitle)
  setMeta('meta[property="og:description"]', 'property', 'og:description', description)
  setMeta('meta[property="og:url"]', 'property', 'og:url', url)
  setMeta('meta[property="og:image"]', 'property', 'og:image', image)
  setMeta('meta[property="og:type"]', 'property', 'og:type', type)

  // Twitter
  setMeta('meta[property="twitter:title"]', 'property', 'twitter:title', fullTitle)
  setMeta('meta[property="twitter:description"]', 'property', 'twitter:description', description)
  setMeta('meta[property="twitter:url"]', 'property', 'twitter:url', url)
  setMeta('meta[property="twitter:image"]', 'property', 'twitter:image', image)

  // Canonical link
  let canonical = document.querySelector('link[rel="canonical"]')
  if (!canonical) {
    canonical = document.createElement('link')
    canonical.setAttribute('rel', 'canonical')
    document.head.appendChild(canonical)
  }
  canonical.setAttribute('href', url)
}

/**
 * Reset SEO to defaults (call when leaving a page)
 */
export function resetSeo() {
  useSeo({})
}

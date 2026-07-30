<template>
  <div class="mb-8">
    <div class="flex items-center justify-between">
      <div 
        v-for="(step, index) in steps" 
        :key="step.id"
        class="flex items-center"
        :class="index < steps.length - 1 ? 'flex-1' : ''"
      >
        <div 
          class="w-10 h-10 rounded-full flex items-center justify-center text-sm font-bold transition-all cursor-pointer"
          :class="{
            'bg-primary-600 text-white': currentStep === index,
            'bg-primary-100 text-primary-700': currentStep > index,
            'bg-slate-200 text-slate-500': currentStep < index
          }"
          @click="$emit('go-to-step', index)"
        >
          <svg v-if="currentStep > index" class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"/>
          </svg>
          <span v-else>{{ index + 1 }}</span>
        </div>
        <div 
          v-if="index < steps.length - 1" 
          class="flex-1 h-1 mx-2 rounded"
          :class="currentStep > index ? 'bg-primary-500' : 'bg-slate-200'"
        ></div>
      </div>
    </div>
    <div class="flex justify-between mt-2">
      <span 
        v-for="(step, index) in steps" 
        :key="step.id"
        class="text-xs font-medium"
        :class="currentStep >= index ? 'text-primary-700' : 'text-slate-400'"
      >
        {{ step.label }}
      </span>
    </div>
  </div>
</template>

<script setup>
defineProps({
  steps: Array,
  currentStep: Number
})

defineEmits(['go-to-step'])
</script>

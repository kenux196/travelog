<template>
  <div class="text-center">
    <h1 class="text-3xl font-bold py-5">관리자 메인 페이지</h1>
    <div class="text-red-500 flex">
      <svg
        xmlns="http://www.w3.org/2000/svg"
        fill="none"
        viewBox="0 0 24 24"
        strokeWidth="{1.5}"
        stroke="currentColor"
        className="w-6 h-6"
      >
        <path
          strokeLinecap="round"
          strokeLinejoin="round"
          d="M12 9v3.75m9-.75a9 9 0 11-18 0 9 9 0 0118 0zm-9 3.75h.008v.008H12v-.008z"
        />
      </svg>
      <p class="font-semibold text-3xl">관리자가 아니면 접근할 수 없다.</p>
    </div>
  </div>
</template>
<script setup></script>

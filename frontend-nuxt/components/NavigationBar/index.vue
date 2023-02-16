<template>
  <nav>
      <ul>
        <li><strong>KENUX JAMIRO</strong></li>
      </ul>
      <ul>
        <li>
          <NuxtLink to="/">Home</NuxtLink>
        </li>
        <li>
          <NuxtLink to="/test">test</NuxtLink>
        </li>
        <li>
          <NuxtLink to="/test2">test2</NuxtLink>
        </li>
        <li>
          <NuxtLink to="/about">About</NuxtLink>
        </li>
        <li>
          <NuxtLink to="/vue-test">Vue.js Test</NuxtLink>
        </li>
        <li>
          <NuxtLink to="/admin">관리자-메인</NuxtLink>
        </li>
        <li>
          <NuxtLink to="/admin/member">관리자-회원관리</NuxtLink>
        </li>
        <li>
          <a href="#">Logout</a>
          <NuxtLink>Login</NuxtLink>
        </li>
      </ul>
    </nav>
</template>

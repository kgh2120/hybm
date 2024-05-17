importScripts(
  "https://www.gstatic.com/firebasejs/9.1.3/firebase-app-compat.js"
);
importScripts(
  "https://www.gstatic.com/firebasejs/9.1.3/firebase-messaging-compat.js"
);

firebase.initializeApp({
  apiKey: "AIzaSyA36ekKWZOdOkcoHgoZLnG28YA9LFJJsN8",
  authDomain: "haveyoubeenmart.firebaseapp.com",
  projectId: "haveyoubeenmart",
  storageBucket: "haveyoubeenmart.appspot.com",
  messagingSenderId: "26693461212",
  appId: "1:26693461212:web:59e1b86728817c4e760019",
  measurementId: "G-RJ06HJSH52",
});

const messaging = firebase.messaging();

messaging.onBackgroundMessage(function (payload) {
  console.log(
    "[firebase-messaging-sw.js] Received background message ",
    "여기서부터 페이로드다ㅏㅏㅏㅏㅏ", payload
  );

  const notificationTitle = payload.notification.title;
  const notificationOptions = {
    body: payload.notification.body,
    icon: '/assets/icon/logo.png',
  };

  self.registration.showNotification(
    notificationTitle,
    notificationOptions
  );
});

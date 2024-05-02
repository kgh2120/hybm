FROM node:18-alpine AS build
WORKDIR /app
COPY . .
RUN npm install --legacy-peer-deps
RUN ls -a
RUN yarn build

FROM nginx:stable-alpine
RUN rm -rf /etc/nginx/conf.d
COPY conf /etc/nginx
COPY --from=build /app/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]

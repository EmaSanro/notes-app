FROM node:22.21.1 AS build
WORKDIR /app
COPY . .
RUN npm install
RUN npm run build --prod

FROM nginx:alpine
RUN rm /etc/nginx/conf.d/default.conf

COPY nginx.conf /etc/nginx/conf.d
COPY --from=build /app/dist/frontend/browser /usr/share/nginx/html
EXPOSE 80
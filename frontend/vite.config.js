import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

export default defineConfig({
  plugins: [react()],
  server: {
    port: 3000, // Port for development server
  },
  build: {
    outDir: "dist", // Directory for production build
  },
});

import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react-swc';
import { VitePWA } from 'vite-plugin-pwa';
import svgr from 'vite-plugin-svgr';

// https://vitejs.dev/config/
export default defineConfig({
	plugins: [
		react(),
		svgr(),
		VitePWA({
			registerType: 'autoUpdate',
			manifest: {
				name: 'ttoti Projcet',
				short_name: 'ttoti',
				// 파일 세팅 초기화
				// includeAssets: [],
				// 현재 아이콘이 없기 때문에 임시 주석 처리
				// icons: [        ],
			},
		}),
	],
	resolve: {
		alias: [
			{ find: '@', replacement: '/src' },
			{ find: '@assets', replacement: '/src/assets' },
			{ find: '@components', replacement: '/src/components' },
			{ find: '@pages', replacement: '/src/pages' },
			{ find: '@routers', replacement: '/src/routers' },
			{ find: '@styles', replacement: '/src/styles' },
			{ find: '@stores', replacement: '/src/stores' },
		],
	},
});

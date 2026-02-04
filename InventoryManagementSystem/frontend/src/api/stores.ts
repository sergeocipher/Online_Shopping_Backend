import { apiFetch } from './client'
import type { Store, StoreCreatePayload } from '../types'

export async function listStores(): Promise<Store[]> {
  return apiFetch<Store[]>('/api/stores')
}

export async function createStore(payload: StoreCreatePayload): Promise<Store> {
  return apiFetch<Store>('/api/stores', {
    method: 'POST',
    body: JSON.stringify(payload),
  })
}

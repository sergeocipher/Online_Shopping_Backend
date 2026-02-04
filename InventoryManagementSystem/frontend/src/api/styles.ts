import { apiFetch } from './client'
import type { Style, StyleCreatePayload } from '../types'

export async function listStyles(): Promise<Style[]> {
  return apiFetch<Style[]>('/api/styles')
}

export async function createStyle(payload: StyleCreatePayload): Promise<Style> {
  return apiFetch<Style>('/api/styles', {
    method: 'POST',
    body: JSON.stringify(payload),
  })
}

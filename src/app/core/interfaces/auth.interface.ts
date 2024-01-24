export interface AuthenticationRequest {
  email?: string;
  password?: string;
}

export interface AuthenticationResponse {
  accessToken?: string;
  mfaEnabled?: boolean;
  secretImageUri?: string;
}

export interface RegisterRequest {
  name?: string;
  email?: string;
  password?: string;
  role?: string;
  mfaEnabled?: boolean;
}

export interface VerificationRequest {
  email?: string;
  code?: string;
}

export interface UserToken {
  sub: string;
  iat: number;
  exp: number;
}

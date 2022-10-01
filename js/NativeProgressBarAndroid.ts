// @flow
import type {ViewProps} from 'react-native/Libraries/Components/View/ViewPropTypes';
import type {HostComponent} from 'react-native';
import {Int32,Double} from 'react-native/Libraries/Types/CodegenTypes';
import codegenNativeComponent from 'react-native/Libraries/Utilities/codegenNativeComponent';


interface NativeProps extends ViewProps {
    styleAttr?: string;
    color?: Int32;
    indeterminate: boolean;
    progress: Double;
    animating: boolean;
}

export default (codegenNativeComponent<NativeProps>('RNCProgressBar') as HostComponent<NativeProps>);